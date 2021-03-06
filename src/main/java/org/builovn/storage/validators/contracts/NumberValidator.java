package org.builovn.storage.validators.contracts;

import org.builovn.storage.DI.annotation.Component;
import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;

/**
 * Валидатор, отвечающий за проверку номера контракта.
 */

@Component
public class NumberValidator implements IContractValidator<Contract> {
    private static final Class<Contract> contractType = Contract.class;

    /**
     * Метод возвращает класс, который он будет валидировать.
     * @return возвращает класс Contract.
     */
    @Override
    public Class<? extends Contract> getContractType() {
        return contractType;
    }

    /**
     * Метод, отвечающий за проверку номера контракта.
     * @param object контракт для проверки.
     * @return возвращает сообщение со статусом ERROR, если номер меньше нуля, в остальных случаях возвращает ОК.
     */
    @Override
    public Message validate(Contract object) {
        int number = object.getNumber();
        if(number < 0) {
            return new Message("Negative contract number value: " + number, Status.ERROR);
        }
        return new Message("Contract number value is correct: " + number, Status.OK);
    }
}
