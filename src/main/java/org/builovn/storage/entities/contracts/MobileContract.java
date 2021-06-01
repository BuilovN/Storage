package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

/**
 *  Класс описывающий контракт на мобильную связь, наследуюемый от {@link Contract}.
 */
public class MobileContract extends Contract{
    /** Количество минут. */
    @XmlElement(name = "minutes")
    private int minutes;
    /** Количество SMS. */
    @XmlElement(name = "messages")
    private int messages;
    /** Количество ГБайт. */
    @XmlElement(name = "gigaBytes")
    private int gigaBytes;

    /**
     * Конструктор класса MobileContract.
     * @param id id контракта.
     * @param dateStart Дата начала контракта.
     * @param dateEnd Дата окончания контракта.
     * @param number Номер контракта.
     * @param owner Владелец контракта.
     * @param minutes Количество минут.
     * @param messages Количество SMS.
     * @param gigaBytes Количество ГБайт.
     */
    public MobileContract(int id, LocalDate dateStart, LocalDate dateEnd, int number, Person owner, int minutes,
                          int messages, int gigaBytes){
        super(id, dateStart, dateEnd, number, owner);
        this.minutes = minutes;
        this.messages = messages;
        this.gigaBytes = gigaBytes;
    }

    public MobileContract(){};

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
