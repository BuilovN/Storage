package org.builovn.storage.repositories;

public interface IRepository<T> {
    void add(T element);
    T set(int index, T element);
    T remove(int index);
    T get(int index);
    int getSize();
}
