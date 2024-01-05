package com.acfjj.app.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByPersonInfo(PersonInfo personInfo);
	User findByEmail(String email);
	User findByPrivateCode(String privateCode);
	List<User> findByIsAdmin(Boolean isAdmin);
	List<User> findByIsAdminAndValidated(Boolean isAdmin, Boolean Validated);

}
