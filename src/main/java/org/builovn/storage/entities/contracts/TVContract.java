package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;
import java.time.LocalDate;

public class TVContract extends Contract {
    private ChannelPackage channelPackage;

    public TVContract(long id, LocalDate dateStart, LocalDate dateEnd, long number, Person owner, ChannelPackage channelPackage) {
        super(id, dateStart, dateEnd, number, owner);
        this.channelPackage = channelPackage;
    }

    public ChannelPackage getChannelPackage() {
        return channelPackage;
    }

    public void setChannelPackage(ChannelPackage channelPackage) {
        this.channelPackage = channelPackage;
    }
}
