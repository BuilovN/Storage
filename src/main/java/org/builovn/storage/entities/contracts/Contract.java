package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;
import java.time.LocalDate;

/** Абстрактный класс, содержащий общую информацию для всех контрактов. */
public abstract class Contract {
    /** id контракта. */
    private int id;
    /** Дата начала контракта. */
    private LocalDate dateStart;
    /** Дата окончания контракта. */
    private LocalDate dateEnd;
    /** Номер контракта. */
    private int number;
    /** Владелец контракта. */
    private Person owner;

    /**
     * Конструктор класса Contract.
     * @param id id контракта.
     * @param dateStart Дата начала контракта.
     * @param dateEnd Дата окончания контракта.
     * @param number Номер контракта.
     * @param owner Владелец контракта.
     */
    public Contract(int id, LocalDate dateStart, LocalDate dateEnd, int number, Person owner) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.number = number;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
