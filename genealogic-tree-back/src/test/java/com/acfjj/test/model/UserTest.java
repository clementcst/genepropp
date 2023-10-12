package com.acfjj.test.model;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.acfjj.app.model.User;
import com.acfjj.test.CustomTestWatcher;

public class UserTest {
	@RegisterExtension
	static CustomTestWatcher testWatcher = new CustomTestWatcher();
	
	static public List<Object> usersDataTest() {
			return new ArrayList<Object>(Arrays.asList(new Object[][] {
			{"Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "Cergy", "adam@mail", "IncommingHashedPassword"},
			{"Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Tournant-En-Brie", "clement@mail", "IncommingHashedPassword"},
			{"Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Paris Hilton", "jordan@mail", "IncommingHashedPassword"},
			{"Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "Paris", "fabien@mail", "IncommingHashedPassword"},
			{"Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "Paris", "joan@mail", "IncommingHashedPassword"}
		}));
	}
		
	@ParameterizedTest
	@MethodSource("usersDataTest")
	void userConstructorTest(String lastName, String firstName, int gender, LocalDate dateOfBirth, String cityOfBirth, String email, String password) {
		User user = new User(lastName,firstName,gender,dateOfBirth,cityOfBirth,email,password);
//		w
	}
	
	@Test
	void testTest() {
		System.out.println("yesay");
	}
}
