package org.builovn.storage.parsers;

import org.builovn.storage.repositories.IRepository;

import java.io.IOException;

public interface IParser <T> {
    void fromFileToRepository(IRepository<T> repository, String filePath) throws IOException;
}
