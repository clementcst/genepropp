package com.acfjj.app.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.utils.Response;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
@Scope("singleton")
@RequestMapping("user")
public class UserController extends AbstractController {
	
	@GetMapping("/all")
	public Response getUsers() {
		return new Response(userService.getValidatedNonAdminUsers());
	}

	@GetMapping
	public Response getUser(@RequestParam Long userId) {
		return new Response(userService.getUser(userId));
	}
}
