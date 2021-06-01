package org.builovn.storage.parsers.jaxb;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.entities.contracts.InternetContract;
import org.builovn.storage.entities.contracts.MobileContract;
import org.builovn.storage.entities.contracts.TVContract;
import org.builovn.storage.entities.persons.Person;
import org.builovn.storage.repositories.ContractRepository;

import javax.xml.bind.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Парсер репозитория из/в формат XML.
 */
public class XMLParser{
    /**
     * Парсит репозиторий в файл XML.
     * @param contractRepository репозиторий
     * @param file файл XML
     */
    public static void repositoryToXML(ContractRepository contractRepository, File file){
        try{
            JAXBContext context = JAXBContext.newInstance(ContractRepository.class, Contract.class,
                    InternetContract.class, MobileContract.class, TVContract.class, Person.class);
            Marshaller mar= context.createMarshaller();
            mar.marshal(contractRepository, file);
        } catch (JAXBException e){}
    }

    /**
     * Парсит файл XML в репозиторий.
     * @param file файл
     * @return итоговый репозиторий
     * @throws JAXBException ошибка парсинга.
     */
    public static ContractRepository XMLtoRepository(File file) throws JAXBException {
        try {
            JAXBContext context = JAXBContext.newInstance(ContractRepository.class, Contract.class,
                    InternetContract.class, MobileContract.class, TVContract.class, Person.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (ContractRepository) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            throw e;
        }
    }
}
