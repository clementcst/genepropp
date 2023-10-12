package com.acfjj.test.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.service.UserService;
import com.acfjj.test.CustomTestWatcher;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
public class UserServiceTest {
	@RegisterExtension
	static CustomTestWatcher testWatcher = new CustomTestWatcher();
	@Autowired
	private UserService userService;
	
	@Test
	void testGetAllUser() {
		userService.getAllUser();
	}

	@Test
	void testGetUser() {
		fail("Not yet implemented");
	}

	@Test
	void testAddUser() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteUser() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateUser() {
		fail("Not yet implemented");
	}

	@Test
	void testAddUserIdToUserPersonInfo() {
		fail("Not yet implemented");
	}

	@Test
	void testGetUserPersonInfo() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateUserPersonInfo() {
		fail("Not yet implemented");
	}

}
