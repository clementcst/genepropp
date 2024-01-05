package com.acfjj.app.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

public class Constants {
	public static final int MAX_LONG_STRING_LENGTH = 4094;
	public static final int MAX_STRING_LENGTH = 254;
	public static final String DEFAULT_PP_URL = "https://i.pinimg.com/originals/a4/af/12/a4af1288eab8714320fa8453f72d79fd.jpg";
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
			.withLocale(Locale.FRENCH);
	public static final List<Integer> GENDER_LIST = new ArrayList<>(Arrays.asList(1, 2, 0));
	public static final int TREE_PRIVACY_PUBLIC = 0;
	public static final int TREE_PRIVACY_PRIVATE = 1;
	public static final int NODE_PRIVACY_PUBLIC = 2;
	public static final int NODE_PRIVACY_RESTRICTED = 1;
	public static final int NODE_PRIVACY_PRIVATE = 0;
	public static final List<Integer> NODE_PRIVACY_LIST = new ArrayList<>(Arrays.asList(NODE_PRIVACY_PRIVATE, NODE_PRIVACY_PUBLIC, NODE_PRIVACY_RESTRICTED));
	public static final List<Integer> TREE_PRIVACY_LIST = new ArrayList<>(Arrays.asList(TREE_PRIVACY_PUBLIC, TREE_PRIVACY_PRIVATE));
	public static final int DEFAULT_GENDER = 2;
	public static final LocalDate DEFAULT_DATEOFBIRTH = LocalDate.of(1900, 1, 1);
	public static final LocalDate MIN_DATEOFBIRTH_USER = Misc.getLocalDateTime().toLocalDate().minusYears(120);
	public static final LocalDate MAX_DATEOFBIRTH_USER = Misc.getLocalDateTime().toLocalDate().minusYears(18);
	public static final LocalDate MIN_DATEOFBIRTH = Misc.getLocalDateTime().toLocalDate().minusYears(2000);
	public static final LocalDate MAX_DATEOFBIRTH = Misc.getLocalDateTime().toLocalDate();
	public static final int DEFAULT_POSTAL_CODE = 99999;
	public static final int MIN_POSTAL_CODE = 00000;
	public static final int MAX_POSTAL_CODE = 99999;
	public static final String DEFAULT_NO_PHONE = "+0000000000";
	public static final String DEFAULT_NO_SECU = "1234567890123";
	public static final String TEMPORARY_STR_MAKER = "BlorkBlorkTemporary3000&é&é&é&é&++}}";

	public static final List<String> POSSIBLE_LHM_KEYS = Arrays.asList("lastName", "firstName", "gender", "dateOfBirth",
			"countryOfBirth", "cityOfBirth", "email", "password", "noSecu", "noPhone", "nationality", "adress",
			"postalCode", "profilPictureUrl", "dateOfDeath", "privacy", "treePrivacy", "id", "hasChild", "dead",
			"parent1Id", "parent2Id", "partnerId", "exPartnersId", "createdById", "treesId", "treeId", "siblingsId",
			"orphan", "isAUserNode");
	public static final List<String> USER_LHM_REQUIRED_KEYS = Arrays.asList("lastName", "firstName", "gender",
			"dateOfBirth", "countryOfBirth", "cityOfBirth", "email", "password");
	public static final List<String> PROFIL_UPDATE_FORBIDDEN_KEYS = Arrays.asList("lastName", "firstName", "password");
	public static final List<String> POSSIBLE_NODE_ACTIONS = Arrays.asList("PARENT", "PARTNER", "SIBLINGS", "CHILD",
			"UPDATE", "DELETE", "EXPARTNER");
	public static final List<String> POSSIBLE_NODE_ACTIONS_CREATION = Arrays.asList("PARENT", "PARTNER", "SIBLINGS",
			"CHILD", "EXPARTNER");



	public static final String SYSTEM_ADMIN_PRIVATE_CODE = "TheSystemAdmin";
	public static final String SYSTEM_ADMIN_EMAIL = "system.admin.account@gmail.com";
	@SuppressWarnings("serial")
	public static final LinkedHashMap<String, String> SYSTEM_ADMIN_DATA = new LinkedHashMap<>() {
		{
			put("lastName", "System");
			put("firstName", "Admin");
			put("gender", "2");
			put("dateOfBirth", "1930-01-01");
			put("countryOfBirth", "Wakanda");
			put("cityOfBirth", "Bourg Palette");
			put("email", SYSTEM_ADMIN_EMAIL);
			put("password", "TestTest7");
			put("noSecu", "1234567890123");
			put("noPhone", "+33836656565");
			put("nationality", "Wakandai");
			put("adress", "1 Avenue du Bourg Palette");
			put("postalCode", "12000");
			put("profilPictureUrl",
					"https://media.licdn.com/dms/image/C4E03AQEVvI1smo3OVA/profile-displayphoto-shrink_400_400/0/1653388794981?e=1707350400&v=beta&t=IBYKa81wTiNOuRJAjtI47GNLFFujzw4Sh37WFBBjKOc");
		}
	};
}
