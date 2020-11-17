package org.builovn.storage.repositories;

import static org.junit.Assert.assertEquals;

import org.builovn.storage.sorters.BubbleSorter;
import org.builovn.storage.sorters.ISorter;
import org.builovn.storage.sorters.QuickSorter;
import org.junit.Test;
import org.junit.Before;
import org.builovn.storage.entities.contracts.*;
import org.builovn.storage.entities.persons.*;
import org.junit.experimental.theories.suppliers.TestedOn;

import java.time.LocalDate;

/**
 * Класс, тестирующий функционал класса {@link ContractRepository}.
 */
public class ContractRepositoryTest {
    private ContractRepository repository;

    /**
     * Создает пустой {@link ContractRepository} для тестирования перед каждым тестом.
     */
    @Before
    public void setUp(){
        repository = new ContractRepository();
    }

    /**
     * Тестирует возможность создания репозитория с некорректным значением capacity.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCapacity(){
        repository = new ContractRepository(-1);
    }

    /**
     * Тестирует корректность получения элемента репозитория по его Id.
     */
    @Test
    public void testGetContractById(){
        Contract mobileContract = createMobileContract();
        Contract internetContract = createInternetContract();
        Contract tvContract = createTVContract();

        repository.add(mobileContract);
        repository.add(internetContract);
        repository.add(tvContract);

        assertEquals(mobileContract, repository.getById(mobileContract.getId()).get());
        assertEquals(internetContract, repository.getById(internetContract.getId()).get());
        assertEquals(tvContract, repository.getById(tvContract.getId()).get());
    }

    /**
     * Тестирует корректность изменения существующего элемента реопзитория.
     */
    @Test
    public void testSetContract(){
        Contract mobileContract = createMobileContract();
        Contract internetContract = createInternetContract();
        Contract tvContract = createTVContract();

        repository.add(mobileContract);
        repository.add(internetContract);

        assertEquals(mobileContract, repository.set(0, tvContract));
        assertEquals(repository.get(0), tvContract);
    }

    /**
     * Тестирует корректность добавления нового элемента в репозиторий
     */
    @Test
    public void testAddContract(){
        Contract mobileContract = createMobileContract();
        Contract internetContract = createInternetContract();
        Contract tvContract = createTVContract();

        repository.add(mobileContract);
        repository.add(internetContract);
        repository.add(tvContract);

        assertEquals(mobileContract, repository.get(0));
        assertEquals(internetContract, repository.get(1));
        assertEquals(tvContract, repository.get(2));
    }

    /**
     * Тестрирует корректность удаления элемента из репозитория по Id.
     */
    @Test
    public void testRemoveContractById(){
        Contract mobileContract = createMobileContract();
        Contract internetContract = createInternetContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(mobileContract, repository.removeById(mobileContract.getId()).get());
        assertEquals(2, repository.getSize());
    }

    /**
     * Тестирует корректность возвращаемого размера репозитория.
     */
    @Test
    public void testSize(){
        Contract mobileContract = createMobileContract();
        Contract internetContract = createInternetContract();
        Contract tvContract = createTVContract();

        repository.add(internetContract);
        repository.add(mobileContract);
        repository.add(tvContract);

        assertEquals(3, repository.getSize());
    }

    /**
     * Тестирует корректность нахождения элемента по заданным критериям.
     */
    @Test()
    public void testFindElementByCriteria(){
        Contract mobileContract = createMobileContract();
        Contract internetContract = createInternetContract();
        Contract tvContract = createTVContract();

        repository.add(mobileContract);
        repository.add(internetContract);
        repository.add(tvContract);

        assertEquals(mobileContract, repository.find(contract -> mobileContract.getId() == contract.getId()).get());
        assertEquals(internetContract, repository.find(contract -> internetContract.getId() == contract.getId()).get());
        assertEquals(tvContract, repository.find(contract -> tvContract.getId() == contract.getId()).get());
    }

    /**
     * Тестирует корректность сортировки репозитория.
     */
    @Test
    public void sortTest(){
        repository.add(createMobileContract()); //id - 3
        repository.add(createInternetContract()); //id - 1
        repository.add(createTVContract()); //id - 2

        ContractRepository sortedRepository = new ContractRepository();
        sortedRepository.add(createInternetContract()); // id - 1
        sortedRepository.add(createTVContract()); // id - 2
        sortedRepository.add(createMobileContract()); // id - 3

        ISorter sorter = new QuickSorter();
        repository.sort((obj1, obj2) -> Integer.compare(obj1.getId(), obj2.getId()));

        for(int i = 0; i < repository.getSize(); i++){
            assertEquals(repository.get(i).getId(), sortedRepository.get(i).getId());
        }
    }
    /**
     * Метод для создания интернет контракта.
     * @return возвращает созданный контракт.
     */
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
