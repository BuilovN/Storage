package org.builovn.storage.validators.contracts;

import org.builovn.storage.entities.contracts.Contract;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;

import java.time.LocalDate;

/**
 * Валидатор, отвечающий за проверку дат в контракте.
 */
public class DatesValidator implements IContractValidator<Contract>{
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
     * Метод проверяет корректность указанных дат в контрактах. Учитывает случаи, когда дата начала больше чем
     * дата конца контракта, когда дата слишком маленькая(Меньше 2000 года) и когда дата слишком большая(Больше 2050 года).
     * @param object контракт для проверки.
     * @return возвращает сообщение с статусом WARNING в случае, если дата меньше 2000 года или больше 2050 года.
     * Error, в случае если дата начала больше даты конца и ОК в остальных случаях.
     */
    @Override
    public Message validate(Contract object) {
        LocalDate dateStart = object.getDateStart();
        LocalDate dateEnd = object.getDateEnd();
        if (dateStart.isAfter(dateEnd)){
            return new Message("Start date is after end date: " + dateStart + " > " + dateEnd, Status.ERROR);
        }
        if (dateStart.isBefore(LocalDate.of(2000, 1, 1))){
            return new Message("Start date is suspicious: " + dateStart, Status.WARNING);
        }
        if (dateEnd.isAfter(LocalDate.of(2050, 1, 1))){
            return new Message("End date is suspicious: " + dateEnd, Status.WARNING);
        }
        return new Message("Dates are correct: start " + dateStart + ", end " + dateEnd , Status.OK);
    }
}
