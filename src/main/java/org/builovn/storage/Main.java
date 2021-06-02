package org.builovn.storage;

import org.apache.log4j.Logger;
import org.builovn.storage.DI.Injector;
import org.builovn.storage.DI.InjectorException;
import org.builovn.storage.database.ContractRepositoryJDBC;
import org.builovn.storage.database.PoolManager;
import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.parsers.contract.ContractParserCSV;
import org.builovn.storage.parsers.IParser;
import org.builovn.storage.repositories.ContractRepository;
import org.builovn.storage.sorters.QuickSorter;

import java.io.IOException;
import java.sql.SQLException;


public class Main {
    private static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) throws SQLException {
        String fileName = "java.csv";
        String workingDir = System.getProperty("user.dir");
        IParser<Contract> parser = new ContractParserCSV();
        ContractRepository contractRepository = new ContractRepository();

        PoolManager poolManager = new PoolManager("jdbc:postgresql://188.120.237.91:5432/netcracker",
                "keycloak", System.getenv("DB_PASS"));
        ContractRepositoryJDBC jdbcRepository = new ContractRepositoryJDBC(poolManager);

        try{
            Injector.inject(contractRepository);
            Injector.inject(parser);
            Injector.inject(jdbcRepository);
        } catch (InjectorException e){
            e.printStackTrace();
        }

        try {
            parser.fromFileToRepository(contractRepository, workingDir + "/files/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            jdbcRepository.save(contractRepository);
            ContractRepository contractRepositoryFromDatabase = jdbcRepository.get();
        } catch (SQLException e){
            logger.error(e.getMessage());
        }
    }
}
