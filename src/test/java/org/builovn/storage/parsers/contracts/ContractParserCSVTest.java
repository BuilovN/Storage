package org.builovn.storage.parsers.contracts;

import org.builovn.storage.parsers.contract.ContractParserCSV;
import org.builovn.storage.repositories.ContractRepository;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

/**
 * Класс, тестирующий функционал класса {@link ContractParserCSV}
 */
public class ContractParserCSVTest {
    private ContractRepository contractRepository;
    private String workingDir = System.getProperty("user.dir");
    /**
     * Создает пустой {@link ContractRepository} для тестирования перед каждым тестом.
     */
    @Before
    public void setUp(){
        contractRepository = new ContractRepository();
    }

    /**
     * Тестирует корректность размера репозитория, после добавления в него элементов из файла.
     */
    @Test
    public void testAmountOfParsedContracts(){
        ContractParserCSV contractParserCSV = new ContractParserCSV();
        String fileName = "java.csv";
        try {
            contractParserCSV.fromFileToRepository(contractRepository, workingDir + "/files/" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(contractRepository.getSize(), 4);
    }

    /**
     * Тестирует выбрасывание исключения, при некорректном пути до файла.
     * @throws IOException ислючение из-за некорректного пути.
     */
    @Test(expected = IOException.class)
    public void testIncorrectFilePath() throws IOException{
        ContractParserCSV contractParserCSV = new ContractParserCSV();
        String fileName = "IncorrectFilePath.csv";
        contractParserCSV.fromFileToRepository(contractRepository, workingDir + "/files/" + fileName);

    }
}
