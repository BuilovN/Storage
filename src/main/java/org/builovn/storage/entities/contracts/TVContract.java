package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

/**
 *  Класс описывающий контракт на цифровое телевидение, наследуюемый от {@link Contract}.
 */
public class TVContract extends Contract {
    /** Пакет каналов {@link ChannelPackage} */
    @XmlElement(name = "channelPackage")
    private ChannelPackage channelPackage;

    /**
     * Конструктор класса TVContract.
     * @param id id контракта.
     * @param dateStart Дата начала контракта.
     * @param dateEnd Дата окончания контракта.
     * @param number Номер контракта.
     * @param owner Владелец контракта.
     * @param channelPackage Пакет каналов {@link ChannelPackage}
     */
    public TVContract(int id, LocalDate dateStart, LocalDate dateEnd, int number, Person owner, ChannelPackage channelPackage) {
        super(id, dateStart, dateEnd, number, owner);
        this.channelPackage = channelPackage;
    }

    public TVContract(){};

    public ChannelPackage getChannelPackage() {
        return channelPackage;
    }

    public void setChannelPackage(ChannelPackage channelPackage) {
        this.channelPackage = channelPackage;
    }

}
