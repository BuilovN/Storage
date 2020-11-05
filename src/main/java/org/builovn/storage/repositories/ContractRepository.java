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

    @Override
    public Contract get(int index){
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("Incorrect index " + index + "for length " + this.size);
        }
        else {
            return elements[index];
        }
    }

    @Override
    public void add(Contract contract){

    }

    @Override
    public Contract set(int index, Contract element) {
        return null;
    }

    @Override
    public Contract remove(int index) {
        return null;
    }

    public int getSize() {
        return size;
    }

    public Contract getById(int id) {
        return null;
    }

}
