package org.builovn.storage.validators.contracts;

import org.builovn.storage.entities.contracts.InternetContract;
import org.builovn.storage.validators.IContractValidator;
import org.builovn.storage.validators.IValidator;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;

public class NameValidator implements IContractValidator<Contract> {

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
}
