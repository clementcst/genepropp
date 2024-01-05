package com.acfjj.app.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import com.acfjj.app.utils.Constants;
import com.acfjj.app.utils.Misc;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String privateCode;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "person_info_id")
	private PersonInfo personInfo;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "my_tree_id")
	private Tree myTree;

	@OneToMany(targetEntity = Conversation.class)
	@JoinTable(name = "user_conversation1")
	@JsonIgnore
	// conversations where this is user1
	private Set<Conversation> conversations1 = new HashSet<>();
	@OneToMany(targetEntity = Conversation.class)
	@JoinTable(name = "user_conversation2")
	@JsonIgnore
	// conversations where this is user2
	private Set<Conversation> conversations2 = new HashSet<>();

	private String email;
	@Column(length = Constants.MAX_LONG_STRING_LENGTH)
	private String password;
	private Boolean validated;
	private Boolean isAdmin;
	@Column(length = Constants.MAX_LONG_STRING_LENGTH)
	private String noSecu;
	private String noPhone;

	public User() {
		super();
	}

	public User(String lastName, String firstname, int gender, LocalDate dateOfBirth, String countryOfBirth,
			String cityOfBirth, String email, String password, String noSecu, String noPhone, String nationality,
			String adress, int postalCode, String profilPictureUrl) {
		this();
		this.email = email;
		this.password = password;
		this.validated = false;
		this.isAdmin = false;
		this.setNoSecu(noSecu);
		this.setNoPhone(noPhone);
		this.privateCode = User.generatePrivateCode();
		this.personInfo = new PersonInfo(lastName, firstname, gender, dateOfBirth, countryOfBirth, cityOfBirth, false,
				nationality, adress, postalCode, profilPictureUrl);
	}

	/* Getters & Setters */
	public Long getId() {
		return id;
	}

	public String getPrivateCode() {
		return privateCode;
	}

	public void setPrivateCode(String privateCode) {
		this.privateCode = privateCode;
	}

	public PersonInfo getPersonInfo() {
		return personInfo;
	}

	public void setPersonInfo(PersonInfo personInfo) {
		this.personInfo = personInfo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String hashedPassword) {
		this.password = hashedPassword;
	}

	public Boolean isValidated() {
		return validated;
	}

	public void setValidated(Boolean validated) {
		this.validated = validated;
	}

	public Boolean isAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean admin) {
		this.isAdmin = admin;
	}

	public Tree getMyTree() {
		return myTree;
	}

	public void setMyTree(Tree myTree) {
		this.myTree = myTree;
	}

	public Long getMyTreeId() {
		if (Objects.isNull(myTree)) {
			return null;
		}
		return myTree.getId();
	}
	
	public Boolean getIsMyTreePublic() {
		if (Objects.isNull(myTree)) {
			return null;
		}
		return myTree.isPublic();
	}

	@JsonIgnore
	public Set<Conversation> getConversations() {
		Set<Conversation> conversations = new HashSet<>(conversations1);
		conversations.addAll(conversations2);
		return conversations;
	}

	public List<Long> getConversationsId() {
		Set<Conversation> conversations = new HashSet<>(conversations1);
		conversations.addAll(conversations2);
		List<Long> conversationsId = new ArrayList<>();
		for (Conversation conversation : conversations) {
			conversationsId.add(conversation.getId());
		}
		return conversationsId;
	}

	public void addConversation1(Conversation conversation) {
		this.conversations1.add(conversation);
	}

	public void removeConversation1(Conversation conversation) {
		this.conversations1.remove(conversation);
	}

	public void addConversation2(Conversation conversation) {
		this.conversations2.add(conversation);
	}

	public void removeConversation2(Conversation conversation) {
		this.conversations2.remove(conversation);
	}

	public Conversation getConversationWith(User user) {
		for (Conversation conversation : getConversations()) {
			if (conversation.getWithWho(this) == user) {
				return conversation;
			}
		}
		return null;
	}

	public String getNoSecu() {
		return noSecu;
	}

	public void setNoSecu(String noSecu) {
		noSecu = Misc.truncateString(noSecu, Constants.MAX_LONG_STRING_LENGTH);
		this.noSecu = Misc.isSocialSecurityNumberAcceptable(noSecu) ? noSecu : Constants.DEFAULT_NO_SECU; 
	}

	public String getNoPhone() {
		return noPhone;
	}

	public void setNoPhone(String noPhone) {
		noPhone = Misc.truncateString(noPhone, Constants.MAX_STRING_LENGTH);
		this.noPhone = Misc.isPhoneNumberAcceptable(noPhone) ? noPhone : Constants.DEFAULT_NO_PHONE;
	}

	@JsonIgnore
	public String getFullName() {
		return getLastName() + " " + getFirstName();
	}

	@JsonIgnore
	public String getFullNameAndBirthInfo() {
		return getFullName() + " : " + getCountryOfBirth() + ", " + getCityOfBirth() + ", "
				+ getDateOfBirth().toString();
	}

	public String getLastName() {
		return getPersonInfo().getLastName();
	}

	public void setLastName(String lastName) {
		getPersonInfo().setLastName(lastName);
	}

	public String getFirstName() {
		return getPersonInfo().getFirstName();
	}

	public void setFirstName(String firstName) {
		getPersonInfo().setFirstName(firstName);
	}

	public LocalDate getDateOfBirth() {
		return getPersonInfo().getDateOfBirth();
	}

	
	public void setDateOfBirth(LocalDate dateOfBirth) {
		getPersonInfo().setDateOfBirth(dateOfBirth);
	}
	
	public void setDateOfBirth(int year, int month, int day) {
		getPersonInfo().setDateOfBirth(year, month, day);
	}

	public int getGender() {
		return getPersonInfo().getGender();
	}

	public void setGender(int gender) {
		getPersonInfo().setGender(gender);
	}

	public String getCityOfBirth() {
		return getPersonInfo().getCityOfBirth();
	}

	public void setCityOfBirth(String cityOfBirth) {
		getPersonInfo().setCityOfBirth(cityOfBirth);
	}

	public String getCountryOfBirth() {
		return getPersonInfo().getCountryOfBirth();
	}

	public void setCountryOfBirth(String countryOfBirth) {
		getPersonInfo().setCountryOfBirth(countryOfBirth);
	}

	public String getNationality() {
		return getPersonInfo().getNationality();
	}

	public void setNationality(String nationality) {
		getPersonInfo().setNationality(nationality);
	}

	public String getAdress() {
		return getPersonInfo().getAdress();
	}

	public void setAdress(String adress) {
		getPersonInfo().setAdress(adress);
	}

	public int getPostalCode() {
		return getPersonInfo().getPostalCode();
	}

	public void setPostalCode(int postalCode) {
		getPersonInfo().setPostalCode(postalCode);
	}

	public String getProfilPictureUrl() {
		return getPersonInfo().getProfilPictureUrl();
	}

	public void setProfilPictureUrl(String profilPictureUrl) {
		getPersonInfo().setProfilPictureUrl(profilPictureUrl);
	}

	public Long getRelatedNodeId() {
		return Objects.isNull(getPersonInfo().getRelatedNode()) ? null : getPersonInfo().getRelatedNode().getId();
	}
	
	public long getTreeSize() {
		if(Objects.isNull(getMyTree())) {
			return -1;
		}
		return getMyTree().getNodes().size();
	}

	public String getTreeName() {
		if(Objects.isNull(getMyTree())) {
			return null;
		}
		return getMyTree().getName();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		User otherUser = (User) obj;
		return (id != null && otherUser.id != null) ? id.equals(otherUser.id) && email.equals(otherUser.email)
				&& password.equals(otherUser.password) && validated.equals(otherUser.validated)
				&& isAdmin.equals(otherUser.isAdmin) && personInfo.equals(otherUser.personInfo)
				&& noSecu.equals(otherUser.noSecu) && noPhone.equals(otherUser.noPhone) : super.equals(obj);
	}

	@Override
	public String toString() {
		return "User[" + "id=" + id + ", personInfo=" + personInfo + ", email=" + email + ", password=" + password
				+ ", validated=" + validated + ", isAdmin=" + isAdmin + ", noSecu=" + noSecu + ", noPhone=" + noPhone
				+ "]";
	}

	public static String generatePrivateCode() {
		int length = 128;
		StringBuilder sequence = new StringBuilder();
		Random random = new Random();

		for (int i = 0; i < length; i++) {
			char randomChar;
			int randomRange = random.nextInt(3);

			if (randomRange == 0) {
				randomChar = (char) (random.nextInt(10) + 48); // Chiffres ASCII [48, 57]
			} else if (randomRange == 1) {
				randomChar = (char) (random.nextInt(26) + 65); // Lettres majuscules ASCII [65, 90]
			} else {
				randomChar = (char) (random.nextInt(26) + 97); // Lettres minuscules ASCII [97, 122]
			}
			sequence.append(randomChar);
		}
		return sequence.toString();
	}

	public Boolean updateWithLHM(LinkedHashMap<String, String> dataLHM) {
		if(!isUpdatableUsing(dataLHM)) {
			return false;
		}
		Set<String> keys = dataLHM.keySet();
		try {
			this.setLastName(keys.contains("lastaNme") ? dataLHM.get("lastName") : this.getLastName());
			this.setFirstName(keys.contains("firstName") ? dataLHM.get("firstName") : this.getFirstName());
			this.setGender(keys.contains("gender") ? Integer.parseInt(dataLHM.get("gender")) : this.getGender());
			this.setDateOfBirth(keys.contains("dateOfBirth") ? LocalDate.parse(dataLHM.get("dateOfBirth")) : this.getDateOfBirth());
			this.setCountryOfBirth(keys.contains("countryOfBirth") ? dataLHM.get("countryOfBirth") : this.getCountryOfBirth());
			this.setCityOfBirth(keys.contains("cityOfBirth") ? dataLHM.get("cityOfBirth") : this.getCityOfBirth());
			this.setEmail(keys.contains("email") ? dataLHM.get("email") : this.getEmail());
			this.setPassword(keys.contains("password") ? dataLHM.get("password") : this.getPassword());
			this.setNoSecu(keys.contains("noSecu") ? dataLHM.get("noSecu") : this.getNoSecu());
			this.setNoPhone(keys.contains("noPhone") ? dataLHM.get("noPhone") : this.getNoPhone());
			this.setNationality(keys.contains("nationality") ? dataLHM.get("nationality") : this.getNationality());
			this.setAdress(keys.contains("adress") ? dataLHM.get("adress") : this.getAdress());
			this.setPostalCode(keys.contains("postalCode") ? Integer.parseInt(dataLHM.get("postalCode")) : this.getPostalCode());
			this.setProfilPictureUrl(keys.contains("profilPictureUrl") ? dataLHM.get("profilPictureUrl") : (Objects.isNull(this.getProfilPictureUrl()) ? Constants.DEFAULT_PP_URL : this.getProfilPictureUrl()));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static User castLHMAsUser(LinkedHashMap<String, String> dataLHM) {
		if(!isCastableUsing(dataLHM)) {
			return null;
		}
		Set<String> keys = dataLHM.keySet();
		try {
			String lastname = dataLHM.get("lastName");
			String firstname = dataLHM.get("firstName");
			int gender = Integer.parseInt(dataLHM.get("gender"));
			LocalDate dateOfBirth = LocalDate.parse(dataLHM.get("dateOfBirth"));
			String countryOfBirth = dataLHM.get("countryOfBirth");
			String cityOfBirth = dataLHM.get("cityOfBirth");
			String email = dataLHM.get("email");
			String password = dataLHM.get("password");
			String noSecu = keys.contains("noSecu") ? dataLHM.get("noSecu") : null;
			String noPhone = keys.contains("noPhone") ? dataLHM.get("noPhone") : null;
			String nationality = keys.contains("nationality") ? dataLHM.get("nationality") : null;
			String adress = keys.contains("adress") ? dataLHM.get("adress") : null;
			int postalCode = keys.contains("postalCode") ? Integer.parseInt(dataLHM.get("postalCode")) : null;
			String ppUrl = keys.contains("profilPictureUrl") ? dataLHM.get("profilPictureUrl")
					: Constants.DEFAULT_PP_URL;

			return new User(lastname, firstname, gender, dateOfBirth, countryOfBirth, cityOfBirth, email, password,
					noSecu, noPhone, nationality, adress, postalCode, ppUrl);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Boolean isCastableUsing(LinkedHashMap<String, String> dataLHM) {
		Set<String> keys = dataLHM.keySet();
		if (!keys.containsAll(Constants.USER_LHM_REQUIRED_KEYS)) {
			return false;
		}
		for (String key : keys) {
			if (!Constants.POSSIBLE_LHM_KEYS.contains(key)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean isUpdatableUsing(LinkedHashMap<String, String> dataLHM) {
		Set<String> keys = dataLHM.keySet();
		for (String key : keys) {
			if (!Constants.POSSIBLE_LHM_KEYS.contains(key) || Constants.PROFIL_UPDATE_FORBIDDEN_KEYS.contains(key)) {
				return false;
			}
		}
		return true;
	}	
}