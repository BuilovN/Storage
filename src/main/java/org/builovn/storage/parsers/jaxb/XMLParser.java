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
import org.apache.log4j.Logger;

/**
 * Парсер репозитория из/в формат XML.
 */
public class XMLParser{
    private final static Logger logger = Logger.getLogger(XMLParser.class);

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
        } catch (JAXBException e){
            logger.error("Parsing repository to XML file error.");
        }
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
            logger.error("Parsing XML file to repository error.");
            throw e;
        }
    }
}
