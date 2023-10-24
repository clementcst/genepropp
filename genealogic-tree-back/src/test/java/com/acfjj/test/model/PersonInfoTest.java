package com.acfjj.test.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
            {"Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", false},
            {"Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", true},
            {"Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvelle-Zélande", "Paris Hilton", true},
            {"Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", false},
            {"Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", false}
        }));
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testPersonInfoConstructor(String lastName, String firstName, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, boolean isDead) {
        PersonInfo personInfo = new PersonInfo(lastName, firstName, gender, dateOfBirth, countryOfBirth, cityOfBirth, isDead);
        assertAll(() -> {
            assertEquals(lastName, personInfo.getLastName());
            assertEquals(firstName, personInfo.getFirstName());
            assertEquals(gender, personInfo.getGender());
            assertEquals(dateOfBirth, personInfo.getDateOfBirth());
            assertEquals(cityOfBirth, personInfo.getCityOfBirth());
            assertEquals(countryOfBirth, personInfo.getCountryOfBirth());
            assertEquals(isDead, personInfo.isDead());
            assertTrue(personInfo.isOrphan());
        });
    }

    @ParameterizedTest
    @MethodSource("testData")
    void testPersonInfoSetters(String lastName, String firstName, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, boolean isDead) {
        String lastNameTest = "lastNameTest";
        String firstNameTest = "firstNameTest";
        int genderTest = 1;
        int[] dateOfBirthTest = new int[] {1784, 11, 12};
        LocalDate formatedDateOfBirthTest = LocalDate.of(dateOfBirthTest[0], dateOfBirthTest[1], dateOfBirthTest[2]);
        String cityOfBirthTest = "cityOfBirthTest";
        String countryOfBirthTest = "countryOfBirthTest";
        boolean isDeadTest = true;
        Long relatedUserIdTest = 12345L; 
        PersonInfo personInfo = new PersonInfo(lastName, firstName, gender, dateOfBirth,countryOfBirth , cityOfBirth, isDead);

        personInfo.setLastName(lastNameTest);
        assertEquals(lastNameTest, personInfo.getLastName());

        personInfo.setFirstName(firstNameTest);
        assertEquals(firstNameTest, personInfo.getFirstName());

        personInfo.setGender(genderTest);
        assertEquals(genderTest, personInfo.getGender());

        personInfo.setDateOfBirth(dateOfBirthTest[0], dateOfBirthTest[1], dateOfBirthTest[2]);
        assertEquals(formatedDateOfBirthTest, personInfo.getDateOfBirth());

        personInfo.setCityOfBirth(cityOfBirthTest);
        assertEquals(cityOfBirthTest, personInfo.getCityOfBirth());
        
        personInfo.setCountryOfBirth(countryOfBirthTest);
        assertEquals(countryOfBirthTest, personInfo.getCountryOfBirth());

        personInfo.setIsDead(isDeadTest);
        assertEquals(isDeadTest, personInfo.isDead());

        personInfo.setRelated_user_id(relatedUserIdTest);
        assertEquals(relatedUserIdTest, personInfo.getRelated_user_id());
    }

}
