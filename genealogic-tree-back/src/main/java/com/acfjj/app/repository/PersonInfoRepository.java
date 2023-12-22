package com.acfjj.app.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.PersonInfo;

public interface PersonInfoRepository extends CrudRepository<PersonInfo, Long>{
	List<PersonInfo> findByLastName(String lastName);
	List<PersonInfo> findByFirstName(String firstName);
	List<PersonInfo> findByCityOfBirth(String cityName);
	List<PersonInfo> findByCountryOfBirth(String countryName);
	List<PersonInfo> findByDateOfBirth(LocalDate dateOfBirth);
	List<PersonInfo> findByLastNameAndFirstNameAndDateOfBirthAndCountryOfBirthAndCityOfBirth(String lastName, String firstName, LocalDate dateOfBirth, String countryOfBirth, String cityofBirth);
}
