package org.builovn.storage.repositories;

import java.util.Optional;
import java.util.function.Predicate;

public interface IRepository<T> {
    void add(T element);
    T set(int index, T element);
    T remove(int index);
    T get(int index);
    Optional<T> find(Predicate<T> predicate);
    int getSize();
}
