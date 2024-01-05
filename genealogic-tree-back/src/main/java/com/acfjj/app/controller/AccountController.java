package com.acfjj.app.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.acfjj.app.model.Message;
import com.acfjj.app.model.Node;
import com.acfjj.app.model.PersonInfo;
import com.acfjj.app.model.Tree;
import com.acfjj.app.model.User;
import com.acfjj.app.utils.Constants;
import com.acfjj.app.utils.Misc;
import com.acfjj.app.utils.Response;
import com.acfjj.app.utils.ValidationType;

@RestController
@CrossOrigin(origins = "${angular.app.url}")
@Scope("singleton")
@RequestMapping("/account")
public class AccountController extends AbstractController {
	@Autowired
	ConversationController conversationController;

	@GetMapping("/login")
        public Response login(@RequestParam String privateCode, @RequestParam String password) {
        if(!Misc.isStringSafe(password) || !Misc.isStringSafe(privateCode)) {
            return new Response("XSS tentative, your ip has been reported to admins.", false);
        }
        User user = userService.getUserByPrivateCode(privateCode);
        if (Objects.isNull(user)) {
            return new Response("Incorrect private code", false);
        }
        if (!passwordCompare(user, password)) {
            return new Response("Incorrect password", false);
        }
        if (!user.isValidated()) {
            List<Message> validations = conversationService.getUserValidationsOfConcernedUser(user);
            LocalDateTime earliestDateTime =  validations.stream()
                    .map(Message::getMessageDateTime)
                    .min(Comparator.naturalOrder()).orElse(null);
            String frontMsg = "User not validated by Admin. Validation request datetime: " + Misc.formatLocalDateTimeToString(earliestDateTime) + ". ";
            if(Misc.isOneMonthOld(earliestDateTime)) {
                frontMsg += "\nIt's been more than a month, You can consider that it has been refused. \nContact support to get more info";
            } else {
                frontMsg += "\nPlease retry later or contact support to get more info.";
            }
            return new Response(frontMsg, false);
        }
        LinkedHashMap<String, String> resValue = new LinkedHashMap<>();
        resValue.put("userId", user.getId().toString());
        resValue.put("privateCode", user.getPrivateCode());
        return new Response(resValue, "Login Success", true);
        }
	
	@PostMapping("/registration")
	public Response registration(@RequestParam int step, @RequestBody LinkedHashMap<String, String> dataLHM,
			@RequestParam(required = false, defaultValue = "0") Boolean userResponse) {
		if(!User.isCastableUsing(dataLHM)) {
			return new Response("Request Body format is invalid.", false);
		}
		Response LHMCheckResponse = Misc.LHMCheck(dataLHM, "USER");
		if(!LHMCheckResponse.getSuccess()) {
			return LHMCheckResponse;
		}
		User userToRegister = User.castLHMAsUser(dataLHM);
		if (Objects.isNull(userToRegister)) {
			return new Response("A Request Body value format is invalid.", false);
		}
		switch (step) {
		case 1: {
			return registrationStep1(userToRegister, dataLHM);
		}
		case 2: {
			return registrationStep2(userToRegister, dataLHM, userResponse);
		}
		default:
			return new Response("Unexpected parameter: " + step, false);
		}
	}

	public Response registerUser(User user, Node existingNode) {
		// security
		if (!Objects.isNull(userService.getUserByNameAndBirthInfo(user.getLastName(), user.getFirstName(),
				user.getDateOfBirth(), user.getCountryOfBirth(), user.getCityOfBirth()))) {
			return new Response("User with birth info (" + user.getFullNameAndBirthInfo() + ") already exist", false);
		}
		if (!Objects.isNull(userService.getUserByEmail(user.getEmail()))) {
			return new Response("User with email (" + user.getEmail() + ") already exist", false);
		}
		// User
		userService.addUser(user);
		String lastName = user.getLastName();
		String firstName = user.getFirstName();
		user = userService.getUserByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(),
				user.getCountryOfBirth(), user.getCityOfBirth());
		if (Objects.isNull(user)) {
			return new Response("Fail to create user in DB", false);
		}
		// User's Tree
		String treeName = firstName + " " + lastName + "'s Tree";
		treeName = treeService.getUniqueName(new Tree(treeName, 0));
		treeService.addTree(new Tree(treeName, 0));
		Tree tree = treeService.getTreeByName(treeName);
		if (Objects.isNull(tree)) {
			return new Response("Fail to create user's Tree in DB", false);
		}
		// User's Node
		if (!Objects.isNull(existingNode)) {
			nodeService.updateNode(existingNode.getId(), existingNode);
		} else {
			nodeService.addNode(new Node(null, user.getPersonInfo(), user, 2));
		}
		Node node = nodeService.getPublicNodeByNameAndBirthInfo(lastName, firstName, user.getDateOfBirth(),
				user.getCountryOfBirth(), user.getCityOfBirth());
		if (Objects.isNull(node)) {
			return new Response("Fail to create user's Node in DB", false);
		}
		user.getPersonInfo().setRelatedUser(user);
		user.getPersonInfo().setRelatedNode(node);
		treeService.addNodeToTree(tree, node, 1, 0);
		tree = treeService.getTree(tree.getId());
		user.setMyTree(tree);
		userService.updateUser(user.getId(), user);
		user = userService.getUser(user.getId());
		// User Validation
		Response valResponse = sendUserValidationToAdmin(user);
		if (!valResponse.getSuccess()) {
			return valResponse;
		}
		// Global Response
		LinkedHashMap<String, String> responseValue = new LinkedHashMap<String, String>();
		responseValue.put("userId", user.getId().toString());
		responseValue.put("privateCode", user.getPrivateCode());
		return new Response(responseValue, "Success", true);
	}

	private Response registrationStep1(User userToRegister, LinkedHashMap<String, String> data) {
		User userFound = userService.getUserByNameAndBirthInfo(userToRegister.getLastName(),
				userToRegister.getFirstName(), userToRegister.getDateOfBirth(), userToRegister.getCountryOfBirth(),
				userToRegister.getCityOfBirth());
		User userFound2 = userService.getUserByEmail(userToRegister.getEmail());
		Node nodeFound = nodeService.getPublicNodeByNameAndBirthInfo(userToRegister.getLastName(),
				userToRegister.getFirstName(), userToRegister.getDateOfBirth(), userToRegister.getCountryOfBirth(),
				userToRegister.getCityOfBirth());
		if (!Objects.isNull(userFound) || !Objects.isNull(userFound2)) {
			userFound = Objects.isNull(userFound) ? userFound2 : userFound;
			Map<String, Object> responseValue = new LinkedHashMap<String, Object>();
			responseValue.put("nextStep", 1);
			responseValue.put("frontMessage",
					"A user with the same names and birth informations or with same email as been found. Double account are not allowed. Please double check register information.");
			responseValue.put("data", data);
			return new Response(responseValue, "User with same birth info (" + userFound.getFullNameAndBirthInfo()
					+ ") or with same email (" + userFound.getEmail() + ") already exist", false);
		} else if (!Objects.isNull(nodeFound)) {
			Map<String, Object> responseValue = new LinkedHashMap<String, Object>();
			responseValue.put("nextStep", 2);
			responseValue.put("frontMessage",
					"A node with the same names and birth informations as been found. Please double check register information and confirm that it's you. Yes/No");
			responseValue.put("toDisplay", nodeFound.getPersonInfo());
			responseValue.put("data", data);
			return new Response(responseValue,
					"Node with same birth info (" + nodeFound.getFullNameAndBirthInfo() + ")", true);
		} else {
			return registerUser(userToRegister, null);
		}
	}

	private Response registrationStep2(User userToRegister, LinkedHashMap<String, String> data, Boolean userResponse) {
		if (userResponse) {
			Node nodeFound = nodeService.getPublicNodeByNameAndBirthInfo(userToRegister.getLastName(),
					userToRegister.getFirstName(), userToRegister.getDateOfBirth(), userToRegister.getCountryOfBirth(),
					userToRegister.getCityOfBirth());
			if (Objects.isNull(nodeFound)) {
				return new Response(
						"Error while retrieving node that was found before. Go back to step 1 of registration", false);
			}
			userToRegister.setPersonInfo(PersonInfo.mergeUserPersonInfoWithNode(nodeFound, userToRegister));
			return registerUser(userToRegister, nodeFound);
		} else {
			return registrationStep1(userToRegister, data);
		}
	}

	private Response sendUserValidationToAdmin(User userToValidate) {
		if (userToValidate.getEmail().equals(Constants.SYSTEM_ADMIN_EMAIL)) {
			return new Response("No validation is needed for system admin user", true);
		}
		List<User> adminList = userService.getAdminUsers();
		if (!userService.existSystemAdminUser()) {
			Response saacResponse = createTheSystemAdminUser();
			if (!saacResponse.getSuccess()) {
				return saacResponse;
			}
		}
		List<Response> responses = new ArrayList<>();
		User sysAdmin = userService.getSystemAdminUser();
		for (User admin : adminList) {
			if (Objects.isNull(conversationService.getConversationOfUsers(admin, sysAdmin))) {
				responses.add(conversationController.newConversation(admin.getId(), sysAdmin.getId()));
			}
			responses.add(conversationController.newValidationMessage(sysAdmin.getId(), admin.getId(),
					ValidationType.USER_VALIDATION, userToValidate));
		}
		for (Response response : responses) {
			if (!response.getSuccess()) {
				return new Response(responses, "One or more failure occured", false);
			}
		}
		return new Response("User validation messages successfully sent to admins", true);
	}

	private Response createTheSystemAdminUser() {
		Response regResponse = registration(1, Constants.SYSTEM_ADMIN_DATA, false);
		if (regResponse.getSuccess()) {
			@SuppressWarnings("unchecked")
			User sysAdmin = userService
					.getUser(Integer.parseInt(((LinkedHashMap<String, String>) regResponse.getValue()).get("userId")));
			sysAdmin.setPrivateCode(Constants.SYSTEM_ADMIN_PRIVATE_CODE);
			sysAdmin.setValidated(true);
			sysAdmin.setIsAdmin(true);
			userService.updateUser(sysAdmin.getId(), sysAdmin);
			return new Response("System Admin account created", true);
		} else {
			return new Response(regResponse.getMessage(), "Fail to register System Admin account", false);
		}
	}
	
	private Boolean passwordCompare(User user, String password) {
		return user.getPassword().equals(password);
	}
}
