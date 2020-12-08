package org.builovn.storage;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.parsers.contract.ContractParserCSV;
import org.builovn.storage.parsers.IParser;
import org.builovn.storage.repositories.ContractRepository;

import java.io.IOException;


public class Main {
    public static void main(String[] args){
        String fileName = "java.csv";
        String workingDir = System.getProperty("user.dir");
        IParser<Contract> parser = new ContractParserCSV();
        ContractRepository contractRepository = new ContractRepository();
        try {
            parser.fromFileToRepository(contractRepository, workingDir + "/files/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
