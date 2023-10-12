package com.acfjj.app.service;

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
	
	public List<User> getAllUser() {
		List<User> users = new ArrayList<>();
		userRepository.findAll().forEach(user -> {
			users.add(user);
		});
		return users;
	}
	
	public User getUser(long id) {
		return userRepository.findById(id).orElse(null);
	}
	
	public void addUser(User user) {
		personInfoRepository.save(user.getPersonInfo());
		userRepository.save(user);
		addUserIdToUserPersonInfo(user.getId());
	}
	
	public void deleteUser(long id) {
		Long personInfoId = getUser(id).getPersonInfo().getId();
		userRepository.deleteById(id);
		//ajouter verif si le personinfo est orphelin ou pas
		personInfoRepository.deleteById(personInfoId);
	}
	
	public void updateUser(long id, User user) {
		if(getUser(id) != null && user.getId() == id) {
			userRepository.save(user);
		}
	}
	
	public void addUserIdToUserPersonInfo(long userId) {
		PersonInfo personInfo = getUserPersonInfo(userId);
		personInfo.setRelated_user_id(userId);
		updateUserPersonInfo(userId, personInfo);
	}
	
	public PersonInfo getUserPersonInfo(long userId) {
		return getUser(userId).getPersonInfo();
	}
	
	public void updateUserPersonInfo(long userId, PersonInfo personInfo) {
		PersonInfo personInfoToUpdate = getUserPersonInfo(userId);
		if(personInfoToUpdate != null && personInfo.getId() == personInfoToUpdate.getId() ) {
			personInfoRepository.save(personInfo);
		}
	}
}
