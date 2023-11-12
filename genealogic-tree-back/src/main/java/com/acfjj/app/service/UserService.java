package com.acfjj.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.User;
import com.acfjj.app.repository.PersonInfoRepository;
import com.acfjj.app.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	PersonInfoRepository personInfoRepository;
	
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(user -> {
			users.add(user);
		});
		return users;
	}
	
	public User getUser(long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public User getUserByNameAndBirthInfo(String lastName, String firstName, LocalDate dateOfBirth, String countryOfBirth, String cityofBirth) {
		User userFound = null;
		PersonInfo personInfoFound = personInfoRepository.findByLastNameAndFirstNameAndDateOfBirthAndCountryOfBirthAndCityOfBirth(lastName, firstName, dateOfBirth, countryOfBirth, cityofBirth); 
		if(!Objects.isNull(personInfoFound)) {
			userFound = userRepository.findByPersonInfo(personInfoFound);
		}
		return userFound;
	}
	
	public PersonInfo getUserPersonInfo(long userId) {
		return getUser(userId).getPersonInfo();
	}
	
	public void addUser(User user) {
		personInfoRepository.save(user.getPersonInfo());
		userRepository.save(user);
	}
	
	public void deleteUser(long id) {
		PersonInfo personInfo = getUser(id).getPersonInfo();
		userRepository.deleteById(id);
		if(personInfo.isOrphan()) {
			personInfoRepository.delete(personInfo);;
		} else {
			personInfo.setRelatedUser(null);
			personInfoRepository.save(personInfo);
		}
	}
	
	public void updateUser(long id, User user) {
		if(getUser(id) != null && user.getId() == id) {
			userRepository.save(user);
			personInfoRepository.save(user.getPersonInfo());
		}
	}
}
