package org.builovn.storage.parsers.contract;

import org.builovn.storage.entities.contracts.*;
import org.builovn.storage.entities.persons.Gender;
import org.builovn.storage.entities.persons.Person;
import org.builovn.storage.parsers.IParser;
import org.builovn.storage.repositories.IRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ContractParserCSV implements IParser<Contract> {

    @Override
    public void fromFileToRepository(IRepository<Contract> repository, String filePath) throws IOException {
        String line;
        String splitBy = ",";
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            while ((line = br.readLine()) != null) {
                Contract contract;
                try {
                    contract = parseContractByLine(line, splitBy, dateTimeFormat);
                } catch (ContractCSVParserException e){
                    e.printStackTrace();
                    continue;
                }
                repository.add(contract);
            }
        }
    }

    private Person parsePersonByLine(String line, String splitBy, DateTimeFormatter dateTimeFormat) throws ContractCSVParserException {
        String[] entity = line.split(splitBy);
        try{
            String firstName = entity[2];
            String lastName = entity[3];
            String patronymic = entity[4];
            Gender gender = Gender.valueOf(entity[5]);
            LocalDate dateOfBirth = LocalDate.parse(entity[6], dateTimeFormat);
            int passportSerialNumber = Integer.parseInt(entity[7]);
            return new Person(
                    Person.nextId(),
                    firstName,
                    lastName,
                    patronymic,
                    gender,
                    passportSerialNumber,
                    dateOfBirth
            );
        } catch (IndexOutOfBoundsException | IllegalArgumentException | DateTimeException e) {
            throw new ContractCSVParserException(e);
        }
    }

    private Contract parseContractByLine(String line, String splitBy, DateTimeFormatter dateTimeFormat) throws ContractCSVParserException {
        Person person = parsePersonByLine(line, splitBy, dateTimeFormat);
        String[] entity = line.split(splitBy);
        LocalDate startContractDate, endContractDate;
        String contractType, contractData;
        int contractNumber;
        try{
            startContractDate = LocalDate.parse(entity[0], dateTimeFormat);
            endContractDate = LocalDate.parse(entity[1], dateTimeFormat);
            contractType = entity[9];
            contractData = entity[10];
            contractNumber = Integer.parseInt(entity[8]);
        } catch (IndexOutOfBoundsException | DateTimeException | IllegalArgumentException e){
            throw new ContractCSVParserException(e);
        }
        switch(contractType) {
            case ("internet"):
                Contract internetContract;
                try {
                    float netSpeed = Float.parseFloat(contractData);
                    internetContract = new InternetContract(
                            Contract.nextId(),
                            startContractDate,
                            endContractDate,
                            contractNumber,
                            person,
                            netSpeed
                    );
                } catch (IllegalArgumentException e) {
                    throw new ContractCSVParserException(e);
                }

                return internetContract;

            case ("tv"):
                Contract tvContract;
                try {
                    ChannelPackage channelPackage = ChannelPackage.valueOf(contractData);
                    tvContract = new TVContract(
                            Contract.nextId(),
                            startContractDate,
                            endContractDate,
                            contractNumber,
                            person,
                            channelPackage
                    );
                } catch (IllegalArgumentException e) {
                    throw new ContractCSVParserException(e);
                }

                return tvContract;

            case ("mobile"):
                Contract mobileContract;
                try {
                    String[] rate = contractData.split(";");
                    int minutes = Integer.parseInt(rate[0]);
                    int sms = Integer.parseInt(rate[1]);
                    int gigabytes = Integer.parseInt(rate[2]);
                    mobileContract = new MobileContract(
                            Contract.nextId(),
                            startContractDate,
                            endContractDate,
                            contractNumber,
                            person,
                            minutes,
                            sms,
                            gigabytes
                    );
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    throw new ContractCSVParserException(e);
                }
                return mobileContract;
            default:
                throw new ContractCSVParserException("Illegal contract type value");
        }
    }
}
