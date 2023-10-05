package com.acfjj.app.repository;

import org.springframework.data.repository.CrudRepository;

import com.acfjj.app.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	
}
