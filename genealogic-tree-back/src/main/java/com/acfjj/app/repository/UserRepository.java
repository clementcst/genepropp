package com.acfjj.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByPersonInfo(PersonInfo personInfo);
	User findByEmail(String email);
}
