package org.builovn.storage.parsers.jaxb;

import org.builovn.storage.entities.contracts.ChannelPackage;
import org.builovn.storage.entities.contracts.InternetContract;
import org.builovn.storage.entities.contracts.MobileContract;
import org.builovn.storage.entities.contracts.TVContract;
import org.builovn.storage.entities.persons.Gender;
import org.builovn.storage.entities.persons.Person;
import org.builovn.storage.repositories.ContractRepository;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.time.LocalDate;

public class XMLParserTest {
    private String workingDir = System.getProperty("user.dir");

    @Test
    public void contractRepositoryToXMLTest() {
        ContractRepository contractRepository = new ContractRepository();
        contractRepository.add(createInternetContract());
        contractRepository.add(createMobileContract());
        contractRepository.add(createTVContract());

        File file = new File(workingDir + "/files/" + "test-repository.xml");
        XMLParser.repositoryToXML(contractRepository, file);
    }

    @Test
    public void XMLtoContractRepositoryTest() throws JAXBException {
        File file = new File(workingDir + "/files/" + "test-repository.xml");
        ContractRepository contractRepository = XMLParser.XMLtoRepository(file);
        assertEquals(3, contractRepository.getSize());
    }

    private InternetContract createInternetContract(){
        Person person = createPerson();
        return new InternetContract(1, LocalDate.of(2017,2, 8), LocalDate.of(2017,2, 8), 1111, person, 95.5f);
    }

    /**
     * Метод для создания ТВ контракта.
     * @return возвращает созданный контракт.
     */
    private TVContract createTVContract(){
        Person person = createPerson();
        return new TVContract(2, LocalDate.of(2017,2, 8), LocalDate.of(2017,2, 8), 1111, person, ChannelPackage.BASIC);
    }

    /**
     * Метод для создания мобильного контракта.
     * @return возвращает созданный контракт.
     */
    private MobileContract createMobileContract(){
        Person person = createPerson();
        return new MobileContract(3, LocalDate.of(2017,2, 8), LocalDate.of(2017,2, 8), 1111, person, 10, 10, 10);
    }

    /**
     * Метод для создания экземпляра класса {@link Person}
     * @return возврашает созданный объект.
     */
    private Person createPerson(){
        return new Person(1, "Nikita", "Builov", "Olegovich", Gender.MALE, 12313123L, LocalDate.of(2019, 11, 4));
    }
}
