package org.builovn.storage.entities.contracts;

import org.builovn.storage.entities.persons.Person;
import java.time.LocalDate;

public class InternetContract extends Contract {
    private float networkSpeed;
    public InternetContract(long id, LocalDate dateStart, LocalDate dateEnd, long number, Person owner, int networkSpeed){
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
