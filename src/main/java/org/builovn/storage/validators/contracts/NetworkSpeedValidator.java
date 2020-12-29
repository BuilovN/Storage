package org.builovn.storage.validators.contracts;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.entities.contracts.InternetContract;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;

/**
 * Класс, отвечающий за проверку интернет скорости.
 */
public class NetworkSpeedValidator implements IContractValidator<Contract>{
    private static final Class<InternetContract> contractType = InternetContract.class;

    /**
     * Метод возвращает класс, который он будет валидировать.
     * @return возвращает класс InternetContract.
     */
    @Override
    public Class<? extends Contract> getContractType() {
        return contractType;
    }

    /**
     * Метод, проверяющий значение скорости интернета.
     * @param object контракт для проверки.
     * @return возвращает сообщение с статусом ERROR когда скорость интернета является отрицательным значением,
     * статус WARNING, когда скорость больше 1000, и ОК в остальных случаях.
     */
    @Override
    public Message validate(Contract object) {
        if(object.getClass() != contractType){
            return new Message("Incorrect contract type: " + object.getClass().getName()
                    + ", expected: " + contractType.getName(), Status.ERROR);
        }
        InternetContract internetContract = (InternetContract) object;
        float networkSpeed = internetContract.getNetworkSpeed();
        if(networkSpeed < 0)
            return new Message("Negative network speed value: " + networkSpeed, Status.ERROR);
        if(networkSpeed > 1000)
            return new Message("Network speed is suspiciously high: " + networkSpeed, Status.WARNING);
        return new Message("Network speed is correct: " + networkSpeed, Status.OK);
    }
}
