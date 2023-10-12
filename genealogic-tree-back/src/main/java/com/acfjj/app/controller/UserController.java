package com.acfjj.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.User;
import com.acfjj.app.service.UserService;

@RestController
public class UserController{	
	@Autowired
	UserService userService;
		
	/*Test & Examples*/ 
	static private List<User> users = new ArrayList<>(Arrays.asList(
		new User("name", "firstname", 1, LocalDate.of(2023, 10, 9), "Paris", "joan@mail", "89"),
		new User("name", "firstname", 1, LocalDate.of(2023, 10, 9), "Paris", "joan@mail", "89"),
		new User("name", "firstname", 1, LocalDate.of(2023, 10, 9), "Paris", "joan@mail", "89"),
		new User("name", "firstname", 1, LocalDate.of(2023, 10, 9), "Paris", "joan@mail", "89")
	));
	
	/*ici c'est juste des exemple de merde c'est pour ça que ça à l'air useless mais ici il y aura les truc complexe*/
	
	@PostMapping("/usersTest") 
	public void addTestUsers() {
		for (User user : users) {
			userService.addUser(user);
		}
	}
	
	@PostMapping("/user") 
	public void addUser(@RequestBody User user) {
			userService.addUser(user);
	}
	
	@GetMapping("/users") 
	public List<User> getUsers() {
		return userService.getAllUser();
	}
	
	@GetMapping("/user/{id}") 
	public User getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}
	
	@DeleteMapping("user/{id}")
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}
	
	
}