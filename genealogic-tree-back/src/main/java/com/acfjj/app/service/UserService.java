package com.acfjj.app.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Constants;

@Service
@Scope("singleton")
public class UserService extends AbstractService {

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

	public User getUserByNameAndBirthInfo(String lastName, String firstName, LocalDate dateOfBirth,
			String countryOfBirth, String cityofBirth) {
		User userFound = null;
		List<PersonInfo> personInfoFounds = personInfoRepository
				.findByLastNameAndFirstNameAndDateOfBirthAndCountryOfBirthAndCityOfBirth(lastName, firstName,
						dateOfBirth, countryOfBirth, cityofBirth);
		if (!personInfoFounds.isEmpty()) {
			PersonInfo personInfoFound = personInfoFounds.get(0);
			userFound = userRepository.findByPersonInfo(personInfoFound);
		}
		return userFound;
	}

	public User getUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	public User getUserByPrivateCode(String privateCode) {
		return userRepository.findByPrivateCode(privateCode);
	}

	public PersonInfo getUserPersonInfo(long userId) {
		return getUser(userId).getPersonInfo();
	}

	public void addUser(User user) {
		personInfoRepository.save(user.getPersonInfo());
		userRepository.save(user);
		user.getPersonInfo().setRelatedUser(user);
		personInfoRepository.save(user.getPersonInfo());
	}

	public void deleteUser(long id) {
		PersonInfo personInfo = getUser(id).getPersonInfo();
		userRepository.deleteById(id);
		if (personInfo.isOrphan()) {
			personInfoRepository.delete(personInfo);
		} else {
			personInfo.setRelatedUser(null);
			personInfoRepository.save(personInfo);
		}
	}

	public void updateUser(long id, User user) {
		if (getUser(id) != null && user.getId() == id) {
			userRepository.save(user);
			personInfoRepository.save(user.getPersonInfo());
		}
	}

	public Boolean existSystemAdminUser() {
		return !Objects.isNull(getSystemAdminUser());
	}

	public User getSystemAdminUser() {
		return userRepository.findByEmail(Constants.SYSTEM_ADMIN_EMAIL);
	}

	public List<User> getAdminUsers() {
		return userRepository.findByIsAdmin(true);
	}

	public List<User> getValidatedNonAdminUsers() {
		return userRepository.findByIsAdminAndValidated(false, true);
	}
}
