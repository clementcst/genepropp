package com.acfjj.app.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.User;
import com.acfjj.app.service.UserService;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class UserController{	
	@Autowired
	UserService userService;
		
	static private List<User> users = new ArrayList<User>(Arrays.asList(new User[] {
			new User("Bourhara", "Adam", 1, LocalDate.of(2002, 04, 2), "France", "Cergy", "adam@mail", "password1","Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
            new User("Cassiet", "Clement", 1, LocalDate.of(1899, 07, 9), "Péîs", "Tournant-En-Brie", "clement@mail", "password2", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
            new User("Gautier", "Jordan", 1, LocalDate.of(2002, 11, 21), "Nouvelle-Zélande", "Paris Hilton", "jordan@mail", "password3", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
            new User("Cerf", "Fabien", 1, LocalDate.of(2002, 03, 9), "France", "Paris", "fabien@mail", "password4", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64"),
            new User("Legrand", "Joan", 1, LocalDate.of(2002, 10, 26), "France", "Paris", "joan@mail", "password5", "Sécurité socisse", "Telephone ui", "nationality", "adress", 1234, "profilPictureData64")
        }));
	
	/*ici c'est juste des exemple de merde c'est pour ça que ça à l'air useless mais ici il y aura les truc complexe*/
	
	@PostMapping("/users") 
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
		return userService.getAllUsers();
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