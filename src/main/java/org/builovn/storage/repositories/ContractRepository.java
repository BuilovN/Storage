package org.builovn.storage.repositories;

import org.builovn.storage.entities.contracts.Contract;

public class ContractRepository implements IRepository<Contract> {
    private Contract[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;

    public ContractRepository(){
        this(DEFAULT_CAPACITY);
    }

    public ContractRepository(int capacity){
        try {
            this.elements = new Contract[capacity];
            this.size = 0;
        } catch (NegativeArraySizeException e) {
            throw new IllegalArgumentException("Incorrect capacity: " + capacity);
        }
    }

    private void increaseCapacity(){
        int newCapacity = (elements.length * 3)/2 + 1;
        Contract[] newArray = new Contract[newCapacity];
        System.arraycopy(elements, 0, newArray, 0, elements.length);
        elements = newArray;
    }

    private void checkIndex(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Incorrect index " + index + " for length " + this.size);
        }
    }

    @Override
    public Contract get(int index){
        checkIndex(index);
        return elements[index];
    }

    public Contract getById(int id) {
        return findById(id);
    }

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

    @Override
    public Contract set(int index, Contract element) {
        checkIndex(index);
        Contract oldElement = elements[index];
        elements[index] = element;
        return oldElement;
    }

    @Override
    public Contract remove(int index) {
        checkIndex(index);
        Contract removableContract = elements[index];
        for(int i = index; i < size - 1; i++){
            elements[i] = elements[i + 1];
        }
        elements[size] = null;
        size--;
        return removableContract;
    }

    public Contract removeById(int id){
        int indexOfRemovable = findIndexById(id);
        if(indexOfRemovable != -1){
            Contract contractToRemove = elements[indexOfRemovable];
            remove(indexOfRemovable);
            return contractToRemove;
        } else {
            return null;
        }
    }

    public int getSize() {
        return size;
    }

    private Contract findById(int id){
        for (Contract element : elements) {
            if (id == element.getId()) {
                return element;
            }
        }
        return null;
    }

    private int findIndexById(int id){
        for (int i = 0; i < size; i++){
            if(id == elements[i].getId()){
                return i;
            }
        }
        return -1;
    }
}
