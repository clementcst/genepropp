package com.acfjj.app.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Misc {
	public static Integer findMaxFrequency(List<Integer> liste) {
		return liste.stream().collect(Collectors.groupingBy(e -> e, Collectors.counting())).entrySet().stream()
				.max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
	}

	public static LocalDateTime getLocalDateTime() {
		return LocalDateTime.now(ZoneId.of("UTC"));
	}

	public static String formatLocalDateTimeToString(LocalDateTime ldt) {
		return ldt.format(Constants.DATE_TIME_FORMATTER);
	}

	public static LocalDateTime formatStringToLocalDateTime(String date) {
		return LocalDateTime.parse(date, Constants.DATE_TIME_FORMATTER);

	}

	public static boolean isOneMonthOld(LocalDateTime dateTime) {
		long differenceInMonths = ChronoUnit.MONTHS.between(dateTime, getLocalDateTime());
		return differenceInMonths >= 1;
	}

	public static boolean isLink(String input) {
		if (Objects.isNull(input)) {
			return false;
		}
		String regex = "^(http|https|ftp)://[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+(/[^\\s]*)?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.matches();
	}

	public static String truncateString(String inputString, int cutSize) {
		if (Objects.isNull(inputString)) {
			return null;
		}
		return (inputString.length() > cutSize) ? inputString.substring(0, cutSize) : inputString;
	}

	public static boolean isDateOfBirthAcceptable(LocalDate dateOfBirhtToCheck) {
		return isDateBetween(dateOfBirhtToCheck, Constants.MIN_DATEOFBIRTH, Constants.MAX_DATEOFBIRTH);
	}

	public static boolean isDateOfBirthAcceptableForUser(LocalDate dateOfBirhtToCheck) {
		return isDateBetween(dateOfBirhtToCheck, Constants.MIN_DATEOFBIRTH_USER, Constants.MAX_DATEOFBIRTH_USER);
	}

	public static boolean isDateBetween(LocalDate dateToCheck, LocalDate startDate, LocalDate endDate) {
		return (dateToCheck.isEqual(startDate) || dateToCheck.isAfter(startDate))
				&& (dateToCheck.isEqual(endDate) || dateToCheck.isBefore(endDate));
	}

	public static boolean isPostalCodeAcceptable(int postalCode) {
		return isIntBetween(postalCode, Constants.MIN_POSTAL_CODE, Constants.MAX_POSTAL_CODE);
	}

	public static boolean isIntBetween(int intToCheck, int inf, int sup) {
		return intToCheck >= inf & intToCheck <= sup;
	}

	public static boolean isPhoneNumberAcceptable(String noPhone) {
		if (Objects.isNull(noPhone)) {
			return false;
		}
		String phoneRegex = "^(\\+|0\\d{1,4})?[\\d\\s-]+$";
		return noPhone.matches(phoneRegex);
	}

	public static boolean isSocialSecurityNumberAcceptable(String noSecu) {
		if (Objects.isNull(noSecu)) {
			return false;
		}
		String socialSecurityRegex = "^\\d{13}$";
		return noSecu.matches(socialSecurityRegex);
	}

	public static boolean isPasswordAcceptable(String password) {
		if (Objects.isNull(password) || password.length() < 6) {
			return false;
		}
		String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z]).{6,}$";
		return password.matches(passwordRegex);
	}

	public static boolean isEmailAcceptable(String email) {
		if (Objects.isNull(email)) {
			return false;
		}
		String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
		Pattern pattern = Pattern.compile(emailPattern);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public static boolean isStringSafe(String input) {
		if (input == null) {
			return true;
		}
		Pattern xssPattern = Pattern.compile("<script>|</script>", Pattern.CASE_INSENSITIVE);
		return !xssPattern.matcher(input).find();
	}

	public static Response LHMCheck(LinkedHashMap<String, String> LHM, String mode) {
		mode = mode.toUpperCase();
		if (Objects.isNull(mode) || (mode != "NODE" && mode != "USER")) {
			return new Response("Wrong argument in function LHMCheck, mode :" + mode, false);
		}
		String responseStr = "";
		String prevResponseStr;
		String keyErr = "";
		List<String> responseValue = new ArrayList<>();
		Set<String> keys = LHM.keySet();
		for (String key : keys) {
			if (!Constants.POSSIBLE_LHM_KEYS.contains(key)) {
				return new Response(key, "Error key : " + key + " cannot be checked by LHMcheck: key not registered.",
						false);
			}
			prevResponseStr = String.copyValueOf(responseStr.toCharArray());
			String value = LHM.get(key);
			if(!Objects.isNull(value) && !value.equals("")) {
				/* System.out.println(key + " : " + value); */
				responseStr += isStringSafe(value) ? ""
						: "XSS tentative on field" + key + ", your ip has been reported to admins.\n";
				String endWasStr = "\n" + key + " was " + value + "\n||\n";
				switch (key) {
				case "password":
					responseStr += isPasswordAcceptable(value) ? ""
							: "Invalid password. It must be at least 6 characters long and contain at least 1 lowercase and 1 uppercase letter.";
					break;
				case "noPhone":
					responseStr += isPhoneNumberAcceptable(value) ? "" : "Invalid phone Number format." + endWasStr;
					break;
				case "email":
					responseStr += isEmailAcceptable(value) ? "" : "Invalid email format." + endWasStr;
					break;
				case "noSecu":
					responseStr += isSocialSecurityNumberAcceptable(value) ? ""
							: "Invalid Sécurité Social number format. It must be 13 digits length " + endWasStr;
					break;
				case "postalCode":
					try {
						keyErr = "Code Postal must be an integer";
						int postalcode = Integer.parseInt(value);
						keyErr = "Invalid code postal format. Must be an integer between 00000 & 99999";
						responseStr += isPostalCodeAcceptable(postalcode) ? "" : keyErr + endWasStr;
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				case "hasChild":
					try {
						keyErr = "HasChild must be a boolean";
						@SuppressWarnings("unused")
						boolean hasChild = Boolean.parseBoolean(value);
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				case "orphan":
					try {
						keyErr = "orphan must be a boolean";
						@SuppressWarnings("unused")
						boolean orphan = Boolean.parseBoolean(value);
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				case "gender":
					try {
						keyErr = "Gender must be an integer";
						int gender = Integer.parseInt(value);
						keyErr = "Invalid gender value. Must be 0, 1 or 2";
						responseStr += Constants.GENDER_LIST.contains(gender) ? "" : keyErr + endWasStr;
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				case "privacy":
					try {
						keyErr = "Privacy must be an integer";
						int privacy = Integer.parseInt(value);
						keyErr = "Invalid privacy value or format. Must be either " + Constants.NODE_PRIVACY_LIST;
						responseStr += Constants.NODE_PRIVACY_LIST.contains(privacy) ? "" : keyErr + endWasStr;
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				case "treePrivacy":
					try {
						keyErr = "Tree Privacy must be an integer";
						int treePrivacy = Integer.parseInt(value);
						keyErr = "Invalid treePrivacy value or format. Must be either " + Constants.TREE_PRIVACY_LIST;
						responseStr += Constants.TREE_PRIVACY_LIST.contains(treePrivacy) ? "" : keyErr + endWasStr;
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				case "dateOfBirth":
					try {
						keyErr = "Invalid format of " + key;
						LocalDate dateOfBirth = LocalDate.parse(value);
						keyErr = key + "Invalid, must be in range : "
								+ (mode == "USER" ? Constants.MIN_DATEOFBIRTH_USER : Constants.MIN_DATEOFBIRTH) + " -> "
								+ (mode == "USER" ? Constants.MAX_DATEOFBIRTH_USER : Constants.MAX_DATEOFBIRTH);
						responseStr += mode == "USER"
								? (isDateOfBirthAcceptableForUser(dateOfBirth) ? "" : keyErr + endWasStr)
								: (isDateOfBirthAcceptable(dateOfBirth) ? "" : keyErr + endWasStr);
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				case "dateOfDeath":
					try {
						keyErr = "Invalid format of " + key + "\n";
						LocalDate dateOfDeath = LocalDate.parse(value);
						keyErr = key + "Invalid, must be before today.\n";
						responseStr += dateOfDeath.isBefore(Misc.getLocalDateTime().toLocalDate()) ? ""
								: keyErr + endWasStr;
					} catch (Exception e) {
						responseStr += keyErr + endWasStr;
					}
					break;
				default:
					break;
				}
				if (!prevResponseStr.equals(responseStr)) {
					responseValue.add(key);
				}
			}
				
		}
		return responseStr.equals("") ? new Response(null, true) : new Response(responseValue, responseStr, false);
	}

	public static boolean parentIsOlder(String parentBirthDate, String childBirthDate) {
		LocalDate parentDate = formatObjectToLocalDate(parentBirthDate);
		LocalDate childDate = formatObjectToLocalDate(childBirthDate);

		return childDate.isAfter(parentDate.plusYears(15));
	}

	public static boolean parentWasAlive(String parentDeathDate, String childBirthDate) {
		if (Objects.isNull(parentDeathDate)) {
			return true;
		}
		LocalDate parentDate = formatObjectToLocalDate(parentDeathDate);
		LocalDate childDate = formatObjectToLocalDate(childBirthDate);
		if (Objects.isNull(parentDate)) {
			return true;
		}

		return parentDate.isAfter(childDate);
	}

	public static boolean birthIsPossible(String birthDate, String deathDate) {
		if (Objects.isNull(deathDate)) {
			return true;
		}
		LocalDate birth = formatObjectToLocalDate(birthDate);
		LocalDate death = formatObjectToLocalDate(deathDate);
		if (Objects.isNull(death)) {
			return true;
		}

		return death.isAfter(birth);
	}

	public static boolean dateOfDeathIsCredible(String birthDate, String deathDate) {
		if (Objects.isNull(deathDate)) {
			return true;
		}
		LocalDate birth = formatObjectToLocalDate(birthDate);
		LocalDate death = formatObjectToLocalDate(deathDate);
		if (Objects.isNull(death)) {
			return true;
		}

		return death.isBefore(birth.plusYears(120));
	}

	private static LocalDate formatObjectToLocalDate(Object date) {
		if (date == null) {
			throw new IllegalArgumentException("Input cannot be null");
		}
		if (date instanceof LocalDate) {
			return (LocalDate) date;
		}
		try {
			String dateString = date.toString().trim();
			if (dateString.isEmpty()) {
				return null;
			}
			return LocalDate.parse(date.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot convert input to LocalDate", e);
		}
	}

	public static long convertObjectToLong(Object input) {
		if (Objects.isNull(input)) {
			return 0L;
		}
		try {
			return Long.parseLong(input.toString());
		} catch (NumberFormatException e1) {
			try {
				return Integer.parseInt(input.toString());
			} catch (NumberFormatException e2) {
				return -9L;
			}
		}
	}
	
	// Function to convert LinkedHashMap<String, String> to String
    public static String convertToString(LinkedHashMap<String, String> map) {
        StringBuilder jsonString = new StringBuilder("{");
        for (String key : map.keySet()) {
            jsonString.append("\"").append(key).append("\":\"").append(map.get(key)).append("\",");
        }
        if (jsonString.length() > 1) {
            jsonString.deleteCharAt(jsonString.length() - 1); // Remove the trailing comma
        }
        jsonString.append("}");
        return jsonString.toString();
    }

    // Function to convert String to LinkedHashMap<String, String>
    public static LinkedHashMap<String, String> convertFromString(String jsonString) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        if (jsonString != null && jsonString.length() > 2) {
            jsonString = jsonString.substring(1, jsonString.length() - 1); // Remove leading and trailing curly braces
            String[] keyValuePairs = jsonString.split(",");
            for (String pair : keyValuePairs) {
                String[] entry = pair.split(":");
                String key = entry[0].replace("\"", "").trim();
                String value = entry[1].replace("\"", "").trim();
                map.put(key, value);
            }
        }
        return map;
    }
}
