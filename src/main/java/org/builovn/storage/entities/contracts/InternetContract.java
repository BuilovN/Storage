package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;
import java.time.LocalDate;

/**
 * Класс описывающий контракт на интернет, наследуюемый от {@link Contract}.
 */
public class InternetContract extends Contract {
    /** Скорость интернета. */
    private float networkSpeed;

    /**
     * Конструктор класса TVContract.
     * @param id id контракта.
     * @param dateStart Дата начала контракта.
     * @param dateEnd Дата окончания контракта.
     * @param number Номер контракта.
     * @param owner Владелец контракта.
     * @param networkSpeed Скорость интернета.
     */
    public InternetContract(int id, LocalDate dateStart, LocalDate dateEnd, int number, Person owner, int networkSpeed){
        super(id, dateStart, dateEnd, number, owner);
        this.networkSpeed = networkSpeed;
    }

    public float getNetworkSpeed() {
        return networkSpeed;
    }

    public void setNetworkSpeed(float networkSpeed) {
        this.networkSpeed = networkSpeed;
    }
}
