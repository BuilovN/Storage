package org.builovn.storage.sorters;

import org.builovn.storage.repositories.IRepository;

import java.util.Comparator;

public interface ISorter {
    <T> void sort(IRepository<T> repository, Comparator<T> comparator);
}
