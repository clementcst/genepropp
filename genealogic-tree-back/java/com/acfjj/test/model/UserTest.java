package com.acfjj.test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.acfjj.app.GenealogicTreeApplication;
import com.acfjj.app.model.User;

class UserTest {
	static public List<Object[]> usersDataTest() {
		List<Object[]> userDataTestList = new ArrayList<Object[]>(Arrays.asList( 
			new Object[]{"Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "Cergy", "adam@mail", "IncommingHashedPassword"},
			new Object[]{"Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Tournant-En-Brie", "clement@mail", "IncommingHashedPassword"},
			new Object[]{"Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Paris Hilton", "jordan@mail", "IncommingHashedPassword"},
			new Object[]{"Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "Paris", "fabien@mail", "IncommingHashedPassword"},
			new Object[]{"Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "Paris", "joan@mail", "IncommingHashedPassword"}
		));
		return userDataTestList;
	}
//		
//	@ParameterizedTest
//	@MethodSource("usersDataTest")
//	void userConstructorTest(Object[] userData) {
//		String lastName = (String) userData[0];
//        String firstName = (String) userData[1];
//        int gender = (int) userData[2];
//        LocalDate dateOfBirth = (LocalDate) userData[3];
//        String cityOfBirth = (String) userData[4];
//        String email = (String) userData[5];
//        String password = (String) userData[6];
//		User user = new User(lastName,firstName,gender,dateOfBirth,cityOfBirth,email,password);
//		assertNotNull(user);
//	}
	
	@Test
	void testTest() {
		System.out.println("yesay");
	}
}
