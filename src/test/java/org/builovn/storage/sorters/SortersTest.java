package org.builovn.storage.sorters;

import static org.junit.Assert.assertEquals;
import org.builovn.storage.entities.contracts.ChannelPackage;
import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.entities.contracts.TVContract;
import org.builovn.storage.entities.persons.Gender;
import org.builovn.storage.entities.persons.Person;
import org.builovn.storage.repositories.ContractRepository;
import org.builovn.storage.repositories.IRepository;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

/**
 * Класс, тестирующий функционал классов сортировок.
 */
public class SortersTest {
    IRepository<Contract> repository;

    /**
     * Создает пустой {@link ContractRepository} и добавляет случайные контракты для тестирования перед каждым тестом.
     */
    @Before
    public void setUp(){
        repository = new ContractRepository();
        repository.add(createTVContract(5));
        repository.add(createTVContract(1));
        repository.add(createTVContract(3));
        repository.add(createTVContract(9));
        repository.add(createTVContract(8));
    }

    /**
     * Тестирует сортировку класса {@link BubbleSorter}
     */
    @Test
    public void bubbleSortTest(){
        ISorter sorter = new BubbleSorter();
        sortContractRepositoryTest(sorter);
    }

    /**
     * Тестирует сортировку класса {@link QuickSorter}
     */
    @Test
    public void quickSortTest(){
        ISorter sorter = new QuickSorter();
        sortContractRepositoryTest(sorter);
    }

    /**
     * Метод для тестирования сортировки, экземпляр класса которого необходимо передать как параметр.
     * @param sorter экзмепляр тестируемой сортировки.
     */
    private void sortContractRepositoryTest(ISorter sorter){
        IRepository<Contract> sortedRepository = new ContractRepository();
        sortedRepository.add(createTVContract(1));
        sortedRepository.add(createTVContract(3));
        sortedRepository.add(createTVContract(5));
        sortedRepository.add(createTVContract(8));
        sortedRepository.add(createTVContract(9));
        sorter.sort(repository, (obj1, obj2) -> Integer.compare(obj1.getId(), obj2.getId()));

        for(int i = 0; i < repository.getSize(); i++){
            assertEquals(repository.get(i).getId(), sortedRepository.get(i).getId());
        }
    }

    /**
     * Метод для создания экземпляра класса {@link Person}
     * @return возврашает созданный объект.
     */
    private Person createPerson(){
        return new Person(1, "Nikita", "Builov", "Olegovich", Gender.MALE, 12313123L, LocalDate.of(2019, 11, 4));
    }

    /**
     * Метод для создания мобильного контракта.
     * @return возвращает созданный контракт.
     */
    private TVContract createTVContract(int id){
        Person person = createPerson();
        return new TVContract(id, LocalDate.of(2017,2, 8), LocalDate.of(2017,2, 8), 1111, person, ChannelPackage.BASIC);
    }
}
