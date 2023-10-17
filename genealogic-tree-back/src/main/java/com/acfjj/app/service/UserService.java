package com.acfjj.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
		List<User> usersWithLastName = getUsersByLastName(lastName);
		List<User> usersWithFirstName = getUsersByFirstName(firstName);
		List<User> usersWithDateOfBirth = getUsersByDateOfBirth(dateOfBirth);
		List<User> usersWithCityOfBirth = getUsersByCityOfBirth(cityofBirth);
		List<User> usersWithCountryOfBirth = getUsersByCountryOfBirth(countryOfBirth);
		if(usersWithLastName.isEmpty() || usersWithFirstName.isEmpty() || usersWithDateOfBirth.isEmpty() || usersWithCityOfBirth.isEmpty() || usersWithCountryOfBirth.isEmpty()) {
			return null;
		}		
		List<User> foundUsers = usersWithLastName;
		foundUsers.retainAll(usersWithFirstName);
		foundUsers.retainAll(usersWithDateOfBirth);
		foundUsers.retainAll(usersWithCityOfBirth);
		foundUsers.retainAll(usersWithCountryOfBirth);
		return foundUsers.isEmpty() ? null : foundUsers.get(0);
	}
	
	public PersonInfo getUserPersonInfo(long userId) {
		return getUser(userId).getPersonInfo();
	}
	
	public void addUser(User user) {
		personInfoRepository.save(user.getPersonInfo());
		userRepository.save(user);
		addRUIDtoUPI(user.getId());
	}
	
	public void deleteUser(long id) {
		PersonInfo personInfo = getUser(id).getPersonInfo();
		userRepository.deleteById(id);
		if(personInfo.isOrphan()) {
			personInfoRepository.delete(personInfo);;
		} else {
			personInfo.setRelated_user_id(null);
		}
	}
	
	public void updateUser(long id, User user) {
		if(getUser(id) != null && user.getId() == id) {
			userRepository.save(user);
			personInfoRepository.save(user.getPersonInfo());
		}
	}
	
	/*Private Methods*/
	private List<User> getUsersByLastName(String lastName) {
		List<PersonInfo> personInfos = personInfoRepository.findByLastName(lastName);
		if(personInfos.isEmpty()) {
			return null;
		}
		List<User> users = new ArrayList<>();
		for (PersonInfo personInfo : personInfos) {
			users.add(userRepository.findByPersonInfo(personInfo));
		}
		return users.isEmpty() ? null : users;
	}
	
	private List<User> getUsersByFirstName(String firstName) {
		List<PersonInfo> personInfos = personInfoRepository.findByFirstName(firstName);
		if(personInfos.isEmpty()) {
			return null;
		}
		List<User> users = new ArrayList<>();
		for (PersonInfo personInfo : personInfos) {
			users.add(userRepository.findByPersonInfo(personInfo));
		}
		return users.isEmpty() ? null : users;
	}
	
	private List<User> getUsersByCityOfBirth(String cityOfBirth) {
		List<PersonInfo> personInfos = personInfoRepository.findByCityOfBirth(cityOfBirth);
		if(personInfos.isEmpty()) {
			return null;
		}
		List<User> users = new ArrayList<>();
		for (PersonInfo personInfo : personInfos) {
			users.add(userRepository.findByPersonInfo(personInfo));
		}
		return users.isEmpty() ? null : users;
	}
	
	private List<User> getUsersByCountryOfBirth(String countryOfBirth) {
		List<PersonInfo> personInfos = personInfoRepository.findByCountryOfBirth(countryOfBirth);
		if(personInfos.isEmpty()) {
			return null;
		}
		List<User> users = new ArrayList<>();
		for (PersonInfo personInfo : personInfos) {
			users.add(userRepository.findByPersonInfo(personInfo));
		}
		return users.isEmpty() ? null : users;
	}
	
	private List<User> getUsersByDateOfBirth(LocalDate dateOfBirth) {
		List<PersonInfo> personInfos = personInfoRepository.findByDateOfBirth(dateOfBirth);
		if(personInfos.isEmpty()) {
			return null;
		}
		List<User> users = new ArrayList<>();
		for (PersonInfo personInfo : personInfos) {
			users.add(userRepository.findByPersonInfo(personInfo));
		}
		return users.isEmpty() ? null : users;
	}
	
	private void addRUIDtoUPI(long userId) {
		User user = getUser(userId);
		user.getPersonInfo().setRelated_user_id(userId);
		updateUser(userId, user);
	} 
}
