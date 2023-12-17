package com.acfjj.app.utils;

import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;

public class Constants {
	public static final int MAX_STRING_LENGTH = 4096;
	public static final String SYSTEM_ADMIN_PRIVATE_CODE = "TheSystemAdmin";
	public static final String SYSTEM_ADMIN_EMAIL = "system.admin.account@gmail.com";
	@SuppressWarnings("serial")
	public static final LinkedHashMap<String, String> SYSTEM_ADMIN_DATA = new LinkedHashMap<>() {{
	    put("lastName", "System");
	    put("firstName", "Admin");
	    put("gender", "2");
	    put("dateOfBirth", "1700-01-01");
	    put("countryOfBirth", "Wakanda");
	    put("cityOfBirth", "Bourg Palette");
	    put("email", SYSTEM_ADMIN_EMAIL);
	    put("password", "testtest");
	    put("noSecu", "purplerain99");
	    put("noPhone", "12");
	    put("nationality", "Wakandai");
	    put("adress", "1 Avenue du Bourg Palette");
	    put("postalCode", "12000");
	    put("profilPictureUrl", "https://media.licdn.com/dms/image/C4E03AQEVvI1smo3OVA/profile-displayphoto-shrink_400_400/0/1653388794981?e=1707350400&v=beta&t=IBYKa81wTiNOuRJAjtI47GNLFFujzw4Sh37WFBBjKOc"); 
	}};
	
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withLocale(Locale.FRENCH);
}
