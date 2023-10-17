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
import com.acfjj.app.model.User;
import com.acfjj.test.CustomTestWatcher;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class UserTest {
	@RegisterExtension
	static CustomTestWatcher testWatcher = new CustomTestWatcher();
	
	static public List<Object> testData() {
			return new ArrayList<Object>(Arrays.asList(new Object[][] {
			{"Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", "adam@mail", "password1"},
			{"Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", "clement@mail", "password2"},
			{"Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvelle-Zélande", "Paris Hilton", "jordan@mail", "password3"},
			{"Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", "fabien@mail", "password4"},
			{"Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", "joan@mail", "password5"}
		}));
	}
		
	@ParameterizedTest
	@MethodSource("testData")
	void testUserConstructor(String lastName, String firstName, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, String email, String password) {
		User user = new User(lastName,firstName,gender,dateOfBirth,countryOfBirth,cityOfBirth,email,password);
		assertNotNull(user.getPersonInfo());
		assertAll(() -> {
			assertFalse(user.isAdmin());
			assertFalse(user.isValidated());
			assertEquals(lastName, user.getLastName());
			assertEquals(firstName, user.getFirstName());
			assertEquals(gender, user.getGender());
			assertEquals(dateOfBirth, user.getDateOfBirth());
			assertEquals(cityOfBirth, user.getCityOfBirth());
			assertEquals(countryOfBirth, user.getCountryOfBirth());
			assertEquals(email, user.getEmail());
			assertEquals(password, user.getPassword());
		});
	}
	
	@ParameterizedTest
	@MethodSource("testData")
	void testUserSetters(String lastName, String firstName, int gender, LocalDate dateOfBirth, String countryOfBirth, String cityOfBirth, String email, String password) {
		String lastNameTest = "lastNameTest";
		String firstNameTest = "firstNameTest";
		int genderTest = 2;
		int[] dateOfBirthTest = new int[] {1900,10,2};
		LocalDate formatedDateOfBirthTest = LocalDate.of(dateOfBirthTest[0],dateOfBirthTest[1],dateOfBirthTest[2]);
		String cityOfBirthTest = "cityOfBirthTest";
		String countryOfBirthTest = "countryOfBirthTest";
		String emailTest = "emailTest";
		String passwordTest = "passwordTest";
		User user = new User(lastName,firstName,gender,dateOfBirth,countryOfBirth,cityOfBirth,email,password);
		
		user.setLastName(lastNameTest);
        assertEquals(lastNameTest, user.getLastName());

        user.setFirstName(firstNameTest);
        assertEquals(firstNameTest, user.getFirstName());

        user.setGender(genderTest);
        assertEquals(genderTest, user.getGender());

        user.setDateOfBirth(dateOfBirthTest[0], dateOfBirthTest[1], dateOfBirthTest[2]);
        assertEquals(formatedDateOfBirthTest, user.getDateOfBirth());

        user.setCityOfBirth(cityOfBirthTest);
        assertEquals(cityOfBirthTest, user.getCityOfBirth());

        user.setCountryOfBirth(countryOfBirthTest);
        assertEquals(countryOfBirthTest, user.getCountryOfBirth());
	
        user.setEmail(emailTest);
        assertEquals(emailTest, user.getEmail());

        user.setPassword(passwordTest);
        assertEquals(passwordTest, user.getPassword());
        
        user.setIsAdmin(true);
        assertTrue(user.isAdmin());

        user.setValidated(true);
        assertTrue(user.isValidated());
	}
}
