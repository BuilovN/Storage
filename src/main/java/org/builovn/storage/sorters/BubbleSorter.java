package org.builovn.storage.sorters;

import org.builovn.storage.repositories.IRepository;

import java.util.Comparator;

public class BubbleSorter implements ISorter {

    @Override
    public <T> void sort(IRepository<T> repository, Comparator<T> comparator) {
        int repositorySize = repository.getSize();
        for (int i = 0; i < repositorySize-1; i++)
            for (int j = 0; j < repositorySize-i-1; j++){
                if(comparator.compare(repository.get(j), repository.get(j+1)) > 0){
                    T temp = repository.get(j);
                    repository.set(j, repository.get(j+1));
                    repository.set(j+1, temp);
                }
            }
    }

}
