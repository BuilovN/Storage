package org.builovn.storage.validators.contracts;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.entities.contracts.MobileContract;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;

/**
 * Валидатор, отвечающий за проверку значения минут.
 */
public class MinutesValidator implements IContractValidator<Contract> {
    private final static Class<MobileContract> contractType = MobileContract.class;

    /**
     * Метод возвращает класс, который он будет валидировать.
     * @return возвращает класс MobileContract.
     */
    @Override
    public Class<? extends Contract> getContractType() {
        return contractType;
    }

    /**
     * Метод, отвечающий за проверку значения минут в экземпляре класса Contract.
     * @param object контракт для проверки.
     * @return возвращает сообщение с статусом ERROR когда количество минут является отрицательным значение,
     * статус WARNING, когда количество минут больше 10000, и ОК в остальных случаях.
     */
    @Override
    public Message validate(Contract object) {
        if(object.getClass() != contractType){
            return new Message("Incorrect contract type: " + object.getClass().getName()
                    + ", expected: " + contractType.getName(), Status.ERROR);
        }
        MobileContract mobileContract = (MobileContract) object;
        int minutes = mobileContract.getMinutes();
        if(minutes < 0){
            return new Message("Negative minutes value: " + minutes, Status.ERROR);
        }
        else if(minutes > 10000){
            return new Message("Minutes value is suspiciously high: " + minutes, Status.WARNING);
        }
        return new Message("Minutes value is correct: " + minutes, Status.OK);
    }
}
