package org.builovn.storage.entities.persons;

import java.time.LocalDate;
import java.time.Period;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private Gender gender;
    private long passportSerialNumber;
    private LocalDate dateOfBirth;

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

    public int getAge(){
        LocalDate currentDate = LocalDate.now();
        Period diffTime = Period.between(this.dateOfBirth, currentDate);
        return diffTime.getYears();
    }
}
