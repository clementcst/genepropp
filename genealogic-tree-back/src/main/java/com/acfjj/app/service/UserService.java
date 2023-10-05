package com.acfjj.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acfjj.app.model.User;
import com.acfjj.app.repository.PersonInfoRepository;
import com.acfjj.app.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	PersonInfoRepository personInfoRepository;
	
	/*que les methode de base là en dessous, on pourra ajouter des methodes plus complexe 
	 * => select user by name par exemple ou meme des truc en fonction des autres table*/
	
	public List<User> getAll() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(user -> {
			users.add(user);
		});
		return users;
	}
	
	public User get(long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public void add(User user) {
		personInfoRepository.save(user.getPersonInfo());
		userRepository.save(user);
	}
	
	public void delete(long id) {
		Long personInfoId = get(id).getPersonInfo().getId();
		userRepository.deleteById(id);
		personInfoRepository.deleteById(personInfoId);
	}
	
	public void update(long id, User user) {
		if(get(id) != null && user.getId() == id) {
			userRepository.save(user);
		}
	}
}
