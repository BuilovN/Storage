package org.builovn.storage;

import org.builovn.storage.DI.Injector;
import org.builovn.storage.DI.InjectorException;
import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.entities.contracts.InternetContract;
import org.builovn.storage.entities.contracts.MobileContract;
import org.builovn.storage.entities.contracts.TVContract;
import org.builovn.storage.parsers.contract.ContractParserCSV;
import org.builovn.storage.parsers.IParser;
import org.builovn.storage.parsers.jaxb.XMLParser;
import org.builovn.storage.repositories.ContractRepository;
import org.builovn.storage.sorters.QuickSorter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws JAXBException {
        String fileName = "java.csv";
        String workingDir = System.getProperty("user.dir");
        IParser<Contract> parser = new ContractParserCSV();
        ContractRepository contractRepository = new ContractRepository();
        try{
            Injector.inject(contractRepository);
            Injector.inject(parser);
        } catch (InjectorException e){
            e.printStackTrace();
        }

        try {
            parser.fromFileToRepository(contractRepository, workingDir + "/files/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String XMLFileName = "contracts.xml";
        File file = new File(workingDir + "/files/" + XMLFileName);
        XMLParser.repositoryToXML(contractRepository, file);
        ContractRepository contractRepositoryFromXML = XMLParser.XMLtoRepository(file);

    }
}
