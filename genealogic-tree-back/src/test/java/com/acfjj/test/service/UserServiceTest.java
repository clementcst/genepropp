package com.acfjj.test.service;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.acfjj.app.GenTreeApp;
import com.acfjj.app.model.User;
import com.acfjj.app.service.UserService;
import com.acfjj.test.CustomTestWatcher;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@SpringBootTest(classes = GenTreeApp.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(OrderAnnotation.class)
public class UserServiceTest {
    @RegisterExtension
    static CustomTestWatcher testWatcher = new CustomTestWatcher();

    @Autowired
    private UserService userService;

    static public List<User> testData() {
        return new ArrayList<User>(Arrays.asList(new User[] {
            new User("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", "adam@mail", "password1","Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl"),
            new User("Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", "clement@mail", "password2", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl"),
            new User("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvelle-Zélande", "Paris Hilton", "jordan@mail", "password3", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl"),
            new User("Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", "fabien@mail", "password4", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl"),
            new User("Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", "joan@mail", "password5", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureUrl")
        }));
    }
    
    @Test
    @Order(1)
    public void testGetAllUsersWithEmptyUserTable() {
        List<User> users = userService.getAllUsers();
        assertTrue(users.isEmpty());  
    }
    
    @ParameterizedTest
    @MethodSource("testData")
    @Order(2)
    public void testCreateUser(User testUser) {
        userService.addUser(testUser);
        User getUserResult1 = userService.getUserByNameAndBirthInfo(
        		testUser.getLastName(), testUser.getFirstName(),
        		testUser.getDateOfBirth(), testUser.getCountryOfBirth(),
        		testUser.getCityOfBirth());
        assertNotNull(getUserResult1);
        User getUserResult2 = userService.getUser(getUserResult1.getId());
        assertNotNull(getUserResult2);
        assertEquals(getUserResult1, getUserResult2);
    }
    
    @Test
    @Order(3)
    public void testGetAllUsers() {
        List<User> users = userService.getAllUsers();
        assertFalse(users.isEmpty());
        assertEquals(testData().size(), users.size());

        for (User foundUser : users) {
            assertNotNull(foundUser);
        }
    }

//    @ParameterizedTest
//    @MethodSource("testData")
//    @Order(4)
//    public void testUpdateUser(User testUser) {
//        long userId = testUser.getId();
//        User updatedUser = new User("Updated", "User", 2, LocalDate.of(1995, 5, 5),"UpdatedCountry" ,"UpdatedCity", "updated@mail", "newpassword");
//        userService.updateUser(userId, updatedUser);
//        User retrievedUser = userService.getUser(userId);
//        assertNotNull(retrievedUser);
//        assertEquals(updatedUser, retrievedUser);
//    }
//
//    @ParameterizedTest
//    @MethodSource("testData")
//    @Order(5)
//    public void testDeleteUser(User testUser) {
//        long userId = testUser.getId();
//        userService.deleteUser(userId);
//        User deletedUser = userService.getUser(userId);
//        assertNull(deletedUser);
//    }
}
