package com.acfjj.app.controller;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Objects;

import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Misc;
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
	
	@PostMapping("/profil/update")
	public Response updateProfil(@RequestParam Long userId, @RequestBody LinkedHashMap<String,String> dataLHM) {
		if(!User.isUpdatableUsing(dataLHM)) {
			return new Response("Request Body format is invalid.", false);
		}
		Response LHMCheckResponse = Misc.LHMCheck(dataLHM, "USER");
		if(!LHMCheckResponse.getSuccess()) {
			return LHMCheckResponse;
		}
		if(dataLHM.containsKey("email") && !Objects.isNull(userService.getUserByEmail(dataLHM.get("email")))) {
			return new Response("Email already exist.", false);
		}
		User user = userService.getUser(userId);
		if(Objects.isNull(user)) {
			return new Response("User not Found.", false);
		}
		if((dataLHM.containsKey("dateOfBirth") || dataLHM.containsKey("cityOfBirth") || dataLHM.containsKey("countryOfBirth"))) {
			LocalDate dateOfBirth = dataLHM.containsKey("dateOfBirth") ? LocalDate.parse(dataLHM.get("dateOfBirth")) : user.getDateOfBirth();
			String cityOfBirth = dataLHM.containsKey("cityOfBirth") ? dataLHM.get("cityOfBirth") : user.getCityOfBirth();
			String countryOfBirth = dataLHM.containsKey("countryOfBirth") ? dataLHM.get("countryOfBirth") : user.getCountryOfBirth();
			User potentialUser = userService.getUserByNameAndBirthInfo(user.getLastName(), user.getFirstName(), dateOfBirth, countryOfBirth, cityOfBirth);
			if (!Objects.isNull(potentialUser) && userId != potentialUser.getId()) {
				return new Response("Cannot update with these information, another user with names & birth infos already exist. Duplicate Account are not allowed", false);
			}
		}
		if(user.updateWithLHM(dataLHM)) {
			userService.updateUser(userId, user);
			if(dataLHM.containsKey("treePrivacy")) {
				Tree tree = user.getMyTree();
				tree.setIsPublic(Boolean.parseBoolean(dataLHM.get("treePrivacy")));
				treeService.updateTree(tree.getId(), tree);
			}
			return new Response("User profil updated successfully", true);
		} else {
			return new Response("A Request Body value format is invalid.", false);
		}
	}
		
	}