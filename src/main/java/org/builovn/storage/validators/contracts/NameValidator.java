package org.builovn.storage.validators.contracts;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.entities.contracts.InternetContract;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;

/**
 * Валидатор, который отвечает за проверку имени владельца контракта.
 */
public class NameValidator implements IContractValidator<Contract> {
    private static final Class<Contract> contractType = Contract.class;

    /**
     * Метод, отвечающий за корректную проверку имени владельца контракта.
     * @param object контракт для проверки.
     * @return возвращает сообщение со статусом ERROR, если имя содержит какие либо знаки кроме символов английского
     * алфавита, либо если имя состоит из 1 символа, либо превышает 50 символов. В остальных случаях возвращает ОК.
     */
    @Override
    public Message validate(Contract object) {
        String name = object.getOwner().getFirstName();
        if (2 < name.length() && name.length() < 50){
            name = name.toLowerCase();
            char[] charArray = name.toCharArray();
            for (char element: charArray){
                if(element < 'a' || element > 'z' )
                    return new Message("Incorrect signs in name: " + name, Status.ERROR);
            }
        } else {
            return new Message("Incorrect name length: " + name , Status.ERROR);
        }
        return new Message("Name is correct: " + name, Status.OK);
    }

    /**
     * Метод возвращает класс, который он будет валидировать.
     * @return возвращает класс Contract.
     */
    @Override
    public Class<? extends Contract> getContractType() {
        return contractType;
    }
}
