package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;
import java.time.LocalDate;

public class Contract {
    private long id;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private long number;
    private Person owner;

    public Contract(long id, LocalDate dateStart, LocalDate dateEnd, long number, Person owner) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.number = number;
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
