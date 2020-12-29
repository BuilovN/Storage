package org.builovn.storage.validators.contracts;

import org.builovn.storage.entities.contracts.InternetContract;
import org.builovn.storage.entities.contracts.MobileContract;
import org.builovn.storage.entities.persons.Gender;
import org.builovn.storage.entities.persons.Person;
import org.builovn.storage.validators.Message;
import org.builovn.storage.validators.Status;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

public class ValidatorsTest {
    static NameValidator nameValidator;
    static MinutesValidator minutesValidator;
    static NetworkSpeedValidator networkSpeedValidator;
    static NumberValidator numberValidator;
    static DatesValidator datesValidator;

    Person correctPerson;
    Person incorrectPerson;
    InternetContract internetContract;
    InternetContract incorrectInternetContract;
    MobileContract mobileContract;
    MobileContract incorrectMobileContract;

    @BeforeClass
    public static void setUpBeforeClass(){
        nameValidator = new NameValidator();
        minutesValidator = new MinutesValidator();
        networkSpeedValidator = new NetworkSpeedValidator();
        numberValidator = new NumberValidator();
        datesValidator = new DatesValidator();
    }

    @Before
    public void setUpBefore() {
        correctPerson = new Person(Person.nextId(), "Builov", "Nikita", "Olegovich", Gender.MALE, 1234567890, LocalDate.of(1998, 3, 4));
        incorrectPerson = new Person(Person.nextId(), "Builov123", "Nikita", "Olegovich", Gender.MALE, 1234567890, LocalDate.of(1998, 3, 4));
        internetContract = new InternetContract(
                InternetContract.nextId(),
                LocalDate.of(2010, 10, 20),
                LocalDate.of(2011, 10, 20),
                10000,
                correctPerson,
                78.9f);
        mobileContract = new MobileContract(
                InternetContract.nextId(),
                LocalDate.of(2010, 10, 20),
                LocalDate.of(2011, 10, 20),
                100,
                correctPerson,
                100,
                100,
                100);
    }

    @Test
    public void correctDateValidatorTest() {
        Message message = datesValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.OK);
    }

    @Test
    public void startDateAfterEndDateValidatorTest() {
        internetContract.setDateStart(LocalDate.of(2011, 10, 20));
        internetContract.setDateEnd(LocalDate.of(2010, 10, 20));
        Message message = datesValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.ERROR);
    }

    @Test
    public void smallDateValidatorTest() {
        internetContract.setDateStart(LocalDate.of(1990, 10, 20));
        Message message = datesValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.WARNING);
    }

    @Test
    public void bigDateValidatorTest(){
        internetContract.setDateEnd(LocalDate.of(2100, 10,20));
        Message message = datesValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.WARNING);
    }

    @Test
    public void nameContainsWrongSymbolsValidatorTest(){
        internetContract.getOwner().setFirstName("TERMINATOR-800");
        Message message = nameValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.ERROR);
    }

    @Test
    public void correctNameValidatorTest(){
        Message message = nameValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.OK);
    }

    @Test
    public void correctNetworkSpeedValidatorTest(){
        Message message = networkSpeedValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.OK);
    }

    @Test
    public void negativeNetworkSpeedValidatorTest(){
        internetContract.setNetworkSpeed(-10);
        Message message = networkSpeedValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.ERROR);
    }

    @Test
    public void bigNetworkSpeedValidatorTest(){
        internetContract.setNetworkSpeed(100000);
        Message message = networkSpeedValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.WARNING);
    }

    @Test
    public void correctNumberValidatorTest(){
        Message message = numberValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.OK);
    }

    @Test
    public void negativeNumberValidatorTest(){
        internetContract.setNumber(-10);
        Message message = numberValidator.validate(internetContract);
        assertEquals(message.getStatus(), Status.ERROR);
    }

    @Test
    public void correctMinutesValidatorTest(){
        Message message = minutesValidator.validate(mobileContract);
        assertEquals(message.getStatus(), Status.OK);
    }

    @Test
    public void negativeMinutesValidatorTest(){
        mobileContract.setMinutes(-10);
        Message message = minutesValidator.validate(mobileContract);
        assertEquals(message.getStatus(), Status.ERROR);
    }

    @Test
    public void bigAmountOfMinutesValidatorTest(){
        mobileContract.setMinutes(100000);
        Message message = minutesValidator.validate(mobileContract);
        assertEquals(message.getStatus(), Status.WARNING);
    }
}
