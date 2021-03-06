package org.builovn.storage.DI;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.parsers.contract.ContractParserCSV;
import org.builovn.storage.repositories.ContractRepository;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class InjectorTest {
    private ContractRepository repository;

    @Before
    public void setUp(){
        repository = new ContractRepository();
    }

    @Test
    public void testInjection() throws InjectorException {
        Injector.inject(repository);
        assertNotNull(repository.getSorter());
    }

}
