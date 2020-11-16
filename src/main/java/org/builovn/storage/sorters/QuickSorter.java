package org.builovn.storage.sorters;

import org.builovn.storage.repositories.IRepository;

import java.util.Comparator;

public class QuickSorter implements ISorter{

    @Override
    public <T> void sort(IRepository<T> repository, Comparator<T> comparator) {
        quickSort(repository, 0, repository.getSize()-1, comparator);
    }

    private <T> void quickSort(IRepository<T> repository, int from, int to, Comparator<T> comparator){
        if (from < to) {
            int leftIndex = from, rightIndex = to;
            T partitionElement = repository.get((leftIndex + rightIndex) / 2);

            do {
                while (comparator.compare(repository.get(leftIndex), partitionElement) < 0) leftIndex++;
                while (comparator.compare(partitionElement, repository.get(rightIndex)) < 0) rightIndex--;

                if ( leftIndex <= rightIndex) {
                    T tmp = repository.get(leftIndex);
                    repository.set(leftIndex, repository.get(rightIndex));
                    repository.set(rightIndex, tmp);
                    leftIndex++;
                    rightIndex--;
                }

            } while (leftIndex <= rightIndex);

            quickSort(repository, from, rightIndex, comparator);
            quickSort(repository, leftIndex, to, comparator);
        }
    }
}
