package org.builovn.storage.validators.contracts;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.validators.IValidator;

public interface IContractValidator<T extends Contract> extends IValidator<T> {
    public Class<? extends Contract> getContractType();
}
