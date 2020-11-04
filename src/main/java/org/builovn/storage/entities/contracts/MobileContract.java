package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;
import java.time.LocalDate;

public class MobileContract extends Contract{
    private int minutes;
    private int messages;
    private int gigaBytes;

    public MobileContract(long id, LocalDate dateStart, LocalDate dateEnd, long number, Person owner, int minutes,
                          int messages, int gigaBytes){
        super(id, dateStart, dateEnd, number, owner);
        this.minutes = minutes;
        this.messages = messages;
        this.gigaBytes = gigaBytes;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getMessages() {
        return messages;
    }

    public void setMessages(int messages) {
        this.messages = messages;
    }

    public int getGigaBytes() {
        return gigaBytes;
    }

    public void setGigaBytes(int gigaBytes) {
        this.gigaBytes = gigaBytes;
    }
}
