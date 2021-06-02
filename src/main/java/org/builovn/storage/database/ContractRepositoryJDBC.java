package org.builovn.storage.database;

import org.apache.log4j.Logger;
import org.builovn.storage.DI.annotation.Autowired;
import org.builovn.storage.entities.contracts.*;
import org.builovn.storage.entities.persons.Gender;
import org.builovn.storage.entities.persons.Person;
import org.builovn.storage.repositories.ContractRepository;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;
import org.builovn.storage.validators.contracts.IContractValidator;

import javax.xml.validation.Validator;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Класс, отвечающий за сохранение репозитория в базу данных и получения его оттуда.
 */
public class ContractRepositoryJDBC {
    private static Logger logger = Logger.getLogger(ContractRepositoryJDBC.class);

    /** Валидаторы для контрактов */
    @Autowired(clazz = IContractValidator.class)
    private static List<IContractValidator<Contract>> validators = new ArrayList<>();

    /** Пул соединений с базой данных */
    private PoolManager poolManager;

    public ContractRepositoryJDBC(PoolManager poolManager){
        this.poolManager = poolManager;
    }

    public List<IContractValidator<Contract>> getValidators(){
        return validators;
    }

    /**
     * Валидирует контракт на корректность.
     * @param contract контракт
     * @return true, если валидация прошла успешно
     */
    private static boolean validateContract(Contract contract){
        for (IContractValidator<Contract> validator : validators){
            if(contract.getClass() == validator.getContractType() || validator.getContractType() == Contract.class) {
                Message message = validator.validate(contract);
                logger.error(message);
                if(message.getStatus().equals(Status.ERROR)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Сохраняет репозиторий в базу данных
     * @param contractRepository репозиторий
     * @throws SQLException ошибка, при попытке сохранения репозитория в бд
     */
    public void save(ContractRepository contractRepository) throws SQLException {
        Connection connection = poolManager.getConnection();
        for(int i = 0; i < contractRepository.getSize(); i++){
            Contract contract = contractRepository.get(i);
            insertContract(contract, connection);
        }
        poolManager.releaseConnection(connection);
    }

    /**
     * Получения репозитория из базы данных
     * @return репозиторий
     * @throws SQLException ошибка, при попытке получения репозитория из бд
     */
    public ContractRepository get() throws SQLException {
        ContractRepository contractRepository = new ContractRepository();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Connection connection = poolManager.getConnection();
        Map<Integer, Person> persons = new HashMap<>();
        ResultSet personsSet = getAllPersons(connection);
        while (personsSet.next()) {
            persons.put(personsSet.getInt(1), new Person(personsSet.getInt(1), personsSet.getString(2),
                    personsSet.getString(3), personsSet.getString(4),
                    Gender.valueOf(personsSet.getString(5)), personsSet.getLong(6),
                    LocalDate.parse(personsSet.getString(7), dateTimeFormat)));
        }

        ResultSet contractsSet = getAllContracts(connection);
        while (contractsSet.next()) {
            Contract contract = null;
            Person person = persons.get(contractsSet.getInt(10));
            if (contractsSet.getString(5) != null) {
                contract = new InternetContract(
                        contractsSet.getInt(1),
                        LocalDate.parse(contractsSet.getString(2), dateTimeFormat),
                        LocalDate.parse(contractsSet.getString(3), dateTimeFormat),
                        contractsSet.getInt(4),
                        person,
                        contractsSet.getFloat(5)
                );
            }

            if (contractsSet.getString(6) != null) {
                contract = new TVContract(
                        contractsSet.getInt(1),
                        LocalDate.parse(contractsSet.getString(2), dateTimeFormat),
                        LocalDate.parse(contractsSet.getString(3), dateTimeFormat),
                        contractsSet.getInt(4),
                        person,
                        ChannelPackage.valueOf(contractsSet.getString(6))
                );
            }

            if (contractsSet.getString(7) != null) {
                contract = new MobileContract(
                        contractsSet.getInt(1),
                        LocalDate.parse(contractsSet.getString(2), dateTimeFormat),
                        LocalDate.parse(contractsSet.getString(3), dateTimeFormat),
                        contractsSet.getInt(4),
                        person,
                        contractsSet.getInt(7),
                        contractsSet.getInt(8),
                        contractsSet.getInt(9)
                );
            }

            if(!validateContract(contract)){
                continue;
            }
            contractRepository.add(contract);
        }
        poolManager.releaseConnection(connection);
        return contractRepository;
    }

    /**
     * Добавляет сущность Person в таблицу person
     * @param person сущность, которую необходимо добавить
     * @param connection подключение к бд
     * @throws SQLException ошибка добавления
     */
    private void insertPerson(Person person, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO person " +
                "(id,first_name,last_name,patronymic,gender,passport_serial_number,date_of_birth) VALUES (?,?,?,?,?,?,?) ON CONFLICT DO NOTHING ;");
        statement.setInt(1, person.getId());
        statement.setString(2, person.getFirstName());
        statement.setString(3, person.getLastName());
        statement.setString(4, person.getPatronymic());
        statement.setString(5, person.getGender().toString());
        statement.setLong(6, person.getPassportSerialNumber());
        statement.setString(7, person.getDateOfBirth().toString());
        statement.executeUpdate();
    }

    /**
     * Добавляет сущность Contract в таблицу person
     * @param contract сущность, которую необходимо добавить
     * @param connection подключение к бд
     * @throws SQLException ошибка добавления
     */
    private void insertContract(Contract contract, Connection connection) throws SQLException {
        insertPerson(contract.getOwner(), connection);
        PreparedStatement statement;

        if(contract instanceof InternetContract){
            statement = connection.prepareStatement("INSERT INTO contract " +
                    "(id,date_start,date_end,number,person_id,network_speed) VALUES (?,?,?,?,?,?) ON CONFLICT DO NOTHING ;");
            fillStatementWithBaseContract(statement, contract);
            statement.setFloat(6, ((InternetContract) contract).getNetworkSpeed());
            statement.executeUpdate();

        } else if (contract instanceof TVContract){
            statement = connection.prepareStatement("INSERT INTO contract " +
                    "(id,date_start,date_end,number,person_id,channel_package) VALUES (?,?,?,?,?,?) ON CONFLICT DO NOTHING ;");
            fillStatementWithBaseContract(statement, contract);
            statement.setString(6, ((TVContract) contract).getChannelPackage().toString());
            statement.executeUpdate();

        } else if (contract instanceof MobileContract){
            statement = connection.prepareStatement("INSERT INTO contract " +
                    "(id,date_start,date_end,number,person_id,minutes,messages,gigabytes) VALUES (?,?,?,?,?,?,?,?) ON CONFLICT DO NOTHING ;");
            fillStatementWithBaseContract(statement, contract);
            statement.setInt(6, ((MobileContract) contract).getMinutes());
            statement.setInt(7, ((MobileContract) contract).getMessages());
            statement.setInt(8, ((MobileContract) contract).getGigaBytes());
            statement.executeUpdate();
        }
    }

    private void fillStatementWithBaseContract(PreparedStatement statement, Contract contract) throws SQLException {
        statement.setInt(1, contract.getId());
        statement.setString(2, contract.getDateStart().toString());
        statement.setString(3, contract.getDateEnd().toString());
        statement.setInt(4, contract.getNumber());
        statement.setInt(5, contract.getOwner().getId());
    }

    /**
     * Получение всей таблицы целиком
     * @param connection подключениек к бд
     * @param tableName название таблицы
     * @return ResultSet со всем содержимым таблицы
     * @throws SQLException ошибка получения данных
     */
    private ResultSet getAll(Connection connection, String tableName) throws SQLException {
        Statement statement=connection.createStatement();
        String query="select * from " + tableName;
        ResultSet resultSet=statement.executeQuery(query);
        return resultSet;
    }
    /** получает все контракты из таблицы */
    private ResultSet getAllContracts(Connection connection) throws SQLException {
        return getAll(connection, "contract");
    }
    /** получает всех людей из таблицы */
    private ResultSet getAllPersons(Connection connection) throws SQLException {
        return getAll(connection, "person");
    }
}
