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
import com.acfjj.app.service.ConversationService;
import com.acfjj.app.service.UserService;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
public class ConversationController{	
	@Autowired
	UserService userService;
	@Autowired
	ConversationService conversationService;
	
	@PostMapping("/conversation/new") 
	public void addTestUsers(@RequestBody long userId1, @RequestBody long userId2) {
		
	}

}