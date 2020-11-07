package org.builovn.storage.repositories;

import org.builovn.storage.entities.contracts.Contract;

import java.util.Optional;
/**
 * Данный класс используется для того, чтобы хранить объекты типа Contract
*/
public class ContractRepository implements IRepository<Contract> {
    /** Хранит объекты в массиве. */
    private Contract[] elements;
    /** Количество элементов в массиве. */
    private int size;
    /** Стандартный размер создаваемого массива. */
    private static final int DEFAULT_CAPACITY = 10;

    /** Конструктор для создания репозитория с массивом стандартного размера. Не требует никаких параметров. */
    public ContractRepository(){
        this(DEFAULT_CAPACITY);
    }

    /**
     * Конструктор для создания репозитория с массивом указанного размера.
     *  @param capacity определяет исходную величину массива
     *  */
    public ContractRepository(int capacity){
        try {
            this.elements = new Contract[capacity];
            this.size = 0;
        } catch (NegativeArraySizeException e) {
            throw new IllegalArgumentException("Incorrect capacity: " + capacity);
        }
    }

    /**
     * Увеличивает  размер массива в полтора раза + единица
     * Все существующие значения сохраняются.
     */
    private void increaseCapacity(){
        int newCapacity = (elements.length * 3)/2 + 1;
        Contract[] newArray = new Contract[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    /**
     * Метод для проверки индекса. Он не должен быть меньше нуля, или больше или равен количеству элементов в массиве.
     * @param index представляет из себя индекс, который необходимо проверить.
     */
    private void checkIndex(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Incorrect index " + index + " for length " + this.size);
        }
    }

    /**
     * Метод для получения объекта контракта по индексу в массиве.
     * Если введен некорректный индекс, то выбрасывается IndexOutOfBoundsException.
     * @param index индекс элемента в массиве
     * @return возвращает контракт, находящийся по введеному индексу
     */
    @Override
    public Contract get(int index){
        checkIndex(index);
        return elements[index];
    }

    /**
     * Метод для получения объекта контракта по id контракта.
     * Если определенного id не существует, то будет возвращен пустой Optional объект.
     * @param id id контракта, который необходимо вернуть.
     * @return возвращает Optional объект с контрактом внутри, либо же пустой Optional, если такого контракта не нашлось.
     */
    public Optional<Contract> getById(int id) {
        return findById(id);
    }

    /**
     * Добавляет новый контракт в репозиторий.
     * В случае, если не осталось свободных мест в массиве, вызывается метод увеличения массива.
     * Увеличивает значение количества элементов в массиве на единицу.
     * @param contract контракт, который необходимо добавить в репозиторий.
     */
    @Override
    public void add(Contract contract){
        try{
            elements[size] = contract;
        } catch (IndexOutOfBoundsException e){
            increaseCapacity();
            elements[size] = contract;
        }
        size++;
    }

    /**
     * Меняет контракт в массиве с определенным индексом, на новый переданный контракт
     * Если введен некорректный индекс, то выбрасывается IndexOutOfBoundsException.
     * @param index индекс в массиве, в котором произойдет замена элемента.
     * @param element контракт, который заменит уже существующий элемент.
     * @return возвращает заменяемый элемент.
     */
    @Override
    public Contract set(int index, Contract element) {
        checkIndex(index);
        Contract oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    /**
     * Удаляет контракт по его индексу в массиве.
     * Если введен некорректный индекс, то выбрасывается IndexOutOfBoundsException.
     * Уменьшает значение количества элементов в массиве на единицу.
     * @param index индекс элемента, по которому происходит удаление контракта.
     * @return возвращает удаляемый объект контракта.
     */
    @Override
    public Contract remove(int index) {
        checkIndex(index);
        Contract removableContract = elements[index];
        for(int i = index; i < size - 1; i++){
            elements[i] = elements[i + 1];
        }
        elements[size - 1] = null;
        size--;
        return removableContract;
    }

    /**
     * Удаляет контракт по его id.
     * Если определенного id не существует, то будет возвращен пустой Optional объект.
     * Уменьшает значение количества элементов в массиве на единицу.
     * @param id id контракта, который необходимо удалить.
     * @return возвращает Optional объект с контрактом внутри, либо же пустой Optional, если такого контракта не нашлось.
     */
    public Optional<Contract> removeById(int id){
        int indexOfRemovable = findIndexById(id);
        if(indexOfRemovable != -1){
            return Optional.of(remove(indexOfRemovable));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Находит контракт по его id.
     * Если определенного id не существует, то будет возвращен пустой Optional объект.
     * @param id id контракта, который необходимо найти.
     * @return возвращает Optional объект с контрактом внутри, либо же пустой Optional, если такого контракта не нашлось.
     */
    private Optional<Contract> findById(int id){
        for (int i = 0; i < size; i++) {
            if (id == elements[i].getId()) {
                return Optional.of(elements[i]);
            }
        }
        return Optional.empty();
    }

    /**
     * Находит индекс контракта по его id
     * Если определенного id не существует, то будет возвращена минус единица(-1).
     * @param id id контракта, индекс которого необходимо найти.
     * @return возвращает индекс объекта, если удалось его найти, либо же минус единицу(-1) в противном случае.
     */
    private int findIndexById(int id){
        for (int i = 0; i < size; i++){
            if(id == elements[i].getId()){
                return i;
            }
        }
        return -1;
    }

    /**
     * Метод для получения количества элементов в репозитории.
     * @return возращает количество элементов в репозитории.
     */
    @Override
    public int getSize() {
        return size;
    }
}
