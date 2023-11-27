package com.acfjj.app.model;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;




@SuppressWarnings("serial")
@Entity
public class User implements Serializable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    //conversations where this is user1
	private Set<Conversation> conversations1 = new HashSet<>();
    @OneToMany(targetEntity = Conversation.class) 
    @JoinTable(name = "user_conversation2")
    @JsonIgnore
    //conversations where this is user2
	private Set<Conversation> conversations2 = new HashSet<>();
    
    private String email;
    private String password;
    private Boolean validated;
    private Boolean isAdmin;
    private String noSecu;
    private String noPhone;
	
	public User() {
		super();
	}
	public User(
		String lastName, 
		String firstname, 
		int gender, 
		LocalDate dateOfBirth, 
		String countryOfBirth, 
		String cityOfBirth, 
		String email, 
		String password, 
		String noSecu, 
		String noPhone,
		String nationality,
		String adress,
		int postalCode,
		String profilPictureData64
	) {
		this();
		this.email = email;
		this.password = password;
		this.validated = false;
		this.isAdmin = false;
		this.noSecu = noSecu;
		this.noPhone = noPhone;
		this.privateCode = User.generatePrivateCode();
		this.personInfo = new PersonInfo(lastName,firstname,gender,dateOfBirth,countryOfBirth,cityOfBirth,false,nationality,adress,postalCode,profilPictureData64);
	}
	
	/*Getters & Setters*/
	public Long getId() {
		return id;
	}
	public String getPrivateCode() {
		return privateCode;
	}
	public PersonInfo getPersonInfo() {
		return personInfo;
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
		if(Objects.isNull(myTree)) {
			return null;
		}
		return myTree.getId();
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
			if(conversation.getWithWho(this) == user) {
				return conversation;
			}
		}
		return null;
	}
	public String getNoSecu() {
		return noSecu;
	}
	public void setNoSecu(String noSecu) {
		this.noSecu = noSecu;
	}
	public String getNoPhone() {
		return noPhone;
	}
	public void setNoPhone(String noPhone) {
		this.noPhone = noPhone;
	}
	@JsonIgnore
	public String getFullName() {
		return getPersonInfo().getLastName() + " " + getPersonInfo().getFirstName();
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
	public String getProfilPictureData64() {
	    return getPersonInfo().getProfilPictureData64();
	}
	public void setProfilPictureData64(String profilPictureData64) {
	    getPersonInfo().setProfilPictureData64(profilPictureData64);
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
	    return (id != null && otherUser.id != null) ? 
	        id.equals(otherUser.id) &&
	        email.equals(otherUser.email) &&
	        password.equals(otherUser.password) &&
	        validated.equals(otherUser.validated) &&
	        isAdmin.equals(otherUser.isAdmin) &&
	        personInfo.equals(otherUser.personInfo) &&
	        noSecu.equals(otherUser.noSecu) &&
	        noPhone.equals(otherUser.noPhone) :
	        super.equals(obj);
	}

	
	@Override
	public String toString() {
	    return "User[" + 
	            "id=" + id + 
	            ", personInfo=" + personInfo + 
	            ", email=" + email + 
	            ", password=" + password + 
	            ", validated=" + validated + 
	            ", isAdmin=" + isAdmin + 
	            ", noSecu=" + noSecu + 
	            ", noPhone=" + noPhone + 
	        "]";
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
	
	public static User castAsUser(LinkedHashMap<String, String> dataLHM) {
		List<String> requiredKeys = Arrays.asList("lastName", "firstName", "gender", "dateOfBirth", "countryOfBirth", "cityOfBirth", "email", "password", "noSecu", "noPhone", "nationality", "adress", "postalCode");		
		Set<String> keys = dataLHM.keySet();
		if(keys.containsAll(requiredKeys)) {
			int gender;
			int postalCode;
			LocalDate dateOfBirth;
			try {
				gender = Integer.parseInt(dataLHM.get("gender"));
				postalCode = Integer.parseInt(dataLHM.get("postalCode"));
				dateOfBirth = LocalDate.parse(dataLHM.get("dateOfBirth"));
			} catch (Exception e) {
				return null;
			}
			return new User(dataLHM.get("lastName"), dataLHM.get("firstName"), gender, dateOfBirth, dataLHM.get("countryOfBirth"), dataLHM.get("cityOfBirth"), dataLHM.get("email"), dataLHM.get("password"), dataLHM.get("noSecu"), dataLHM.get("noPhone"), dataLHM.get("nationality"), dataLHM.get("adress"), postalCode, null);
		}
		return null;
	}
}