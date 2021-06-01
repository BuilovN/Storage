package org.builovn.storage.entities.persons;

import org.builovn.storage.parsers.jaxb.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.Period;

/**
 * Класс, описывающий человека(Клиента).
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="person")
public class Person {
    @XmlTransient
    static int idCounter = 0;
    /** id человека. */
    @XmlElement(name = "id")
    private int id;
    /** Имя человека. */
    @XmlElement(name = "firstName")
    private String firstName;
    /** Фамилия человека. */
    @XmlElement(name = "lastName")
    private String lastName;
    /** Отчество человека. */
    @XmlElement(name = "patronymic")
    private String patronymic;
    /** Гендер человека {@link Gender}. */
    @XmlElement(name = "gender")
    private Gender gender;
    /** Серия и номер паспорта. */
    @XmlElement(name = "passportSerialNumber")
    private long passportSerialNumber;
    /** Дата рождения. */
    @XmlJavaTypeAdapter(value= LocalDateAdapter.class)
    @XmlElement(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    /**
     * Конструктор класса Person.
     * @param id id человека.
     * @param firstName Имя человека.
     * @param lastName Фамилия человека.
     * @param patronymic Отчество человека.
     * @param gender Гендер человека.
     * @param passportSerialNumber Серия и номер паспорта.
     * @param dateOfBirth Дата рождения.
     */
    public Person(int id, String firstName, String lastName, String patronymic, Gender gender,
                  long passportSerialNumber, LocalDate dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.gender = gender;
        this.passportSerialNumber = passportSerialNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Person(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public long getPassportSerialNumber() {
        return passportSerialNumber;
    }

    public void setPassportSerialNumber(long passportSerialNumber) {
        this.passportSerialNumber = passportSerialNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Возвращает возраст человека.
     * @return возвращает возраст в годах.
     */
    public int getAge(){
        LocalDate currentDate = LocalDate.now();
        Period diffTime = Period.between(this.dateOfBirth, currentDate);
        return diffTime.getYears();
    }

    public static int nextId(){
        int id = idCounter;
        idCounter++;
        return id;
    }
}
