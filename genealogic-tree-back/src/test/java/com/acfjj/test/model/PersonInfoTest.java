package com.acfjj.test.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.test.CustomTestWatcher;


@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class PersonInfoTest {
    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    static public List<Object> testData() {
        return new ArrayList<Object>(Arrays.asList(new Object[][] {
            {"Bourhara", "Adam", 1, LocalDate.of(2002, 4, 2), "France", "Cergy", false, "French", "123 Main St", 12345, "base64image"},
            {"Cassiet", "Clement", 1, LocalDate.of(1899, 7, 9), "Péîs", "Tournant-En-Brie", true, "Island", "456 Elm St", 67890, "base64image"},
            {"Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvelle-Zélande", "Paris Hilton", true, "Kiwi", "789 Oak St", 54321, "base64image"},
            {"Cerf", "Fabien", 1, LocalDate.of(2002, 3, 9), "France", "Paris", false, "French", "234 Birch St", 98765, "base64image"},
            {"Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", false, "French", "345 Cedar St", 23456, "base64image"}
        }));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testPersonInfoConstructor(
        String lastName,
        String firstName,
        int gender,
        LocalDate dateOfBirth,
        String countryOfBirth,
        String cityOfBirth,
        boolean isDead,
        String nationality,
        String address,
        int postalCode,
        String profilPictureUrl
    ) {
        PersonInfo personInfo = new PersonInfo(lastName, firstName, gender, dateOfBirth, countryOfBirth, cityOfBirth, isDead, nationality, address, postalCode, profilPictureUrl);
        assertAll(() -> {
            assertEquals(lastName, personInfo.getLastName());
            assertEquals(firstName, personInfo.getFirstName());
            assertEquals(gender, personInfo.getGender());
            assertEquals(dateOfBirth, personInfo.getDateOfBirth());
            assertEquals(cityOfBirth, personInfo.getCityOfBirth());
            assertEquals(countryOfBirth, personInfo.getCountryOfBirth());
            assertEquals(isDead, personInfo.isDead());
            assertEquals(nationality, personInfo.getNationality());
            assertEquals(address, personInfo.getAdress());
            assertEquals(postalCode, personInfo.getPostalCode());
            assertEquals(profilPictureUrl, personInfo.getProfilPictureUrl());
            assertTrue(personInfo.isOrphan());
        });
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testPersonInfoSetters(
        String lastName,
        String firstName,
        int gender,
        LocalDate dateOfBirth,
        String countryOfBirth,
        String cityOfBirth,
        boolean isDead,
        String nationality,
        String address,
        int postalCode,
        String profilPictureUrl
    ) {
        String lastNameTest = "lastNameTest";
        String firstNameTest = "firstNameTest";
        int genderTest = 1;
        LocalDate dateOfBirthTest = LocalDate.of(2000, 1, 1);
        String cityOfBirthTest = "cityOfBirthTest";
        String countryOfBirthTest = "countryOfBirthTest";
        boolean isDeadTest = true;
        String nationalityTest = "nationalityTest";
        String addressTest = "addressTest";
        int postalCodeTest = 54321;
        String profilPictureUrlTest = "newBase64Image";

        PersonInfo personInfo = new PersonInfo(lastName, firstName, gender, dateOfBirth, countryOfBirth, cityOfBirth, isDead, nationality, address, postalCode, profilPictureUrl);

        personInfo.setLastName(lastNameTest);
        assertEquals(lastNameTest, personInfo.getLastName());

        personInfo.setFirstName(firstNameTest);
        assertEquals(firstNameTest, personInfo.getFirstName());

        personInfo.setGender(genderTest);
        assertEquals(genderTest, personInfo.getGender());

        personInfo.setDateOfBirth(dateOfBirthTest);
        assertEquals(dateOfBirthTest, personInfo.getDateOfBirth());

        personInfo.setCityOfBirth(cityOfBirthTest);
        assertEquals(cityOfBirthTest, personInfo.getCityOfBirth());

        personInfo.setCountryOfBirth(countryOfBirthTest);
        assertEquals(countryOfBirthTest, personInfo.getCountryOfBirth());

        personInfo.setIsDead(isDeadTest);
        assertEquals(isDeadTest, personInfo.isDead());

        personInfo.setNationality(nationalityTest);
        assertEquals(nationalityTest, personInfo.getNationality());

        personInfo.setAdress(addressTest);
        assertEquals(addressTest, personInfo.getAdress());

        personInfo.setPostalCode(postalCodeTest);
        assertEquals(postalCodeTest, personInfo.getPostalCode());

        personInfo.setProfilPictureUrl(profilPictureUrlTest);
        assertEquals(profilPictureUrlTest, personInfo.getProfilPictureUrl());
    }
}