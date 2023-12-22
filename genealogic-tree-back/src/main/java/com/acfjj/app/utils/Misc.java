package com.acfjj.app.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
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

	public static boolean isStringSafe(String input) {
		if (input == null) {
			return true;
		}
		Pattern xssPattern = Pattern.compile("<script>|</script>", Pattern.CASE_INSENSITIVE);
		return !xssPattern.matcher(input).find();
	}

	public static Response LHMCheck(LinkedHashMap<String, String> LHM) {
		String responseStr = "";
		String prevResponseStr;
		String keyErr = "";
		List<String> responseValue = new ArrayList<>();
		Set<String> keys = LHM.keySet();
		for (String key : keys) {
			if (!Constants.POSSIBLE_LHM_KEYS.contains(key)) {
				return new Response("Error key : " + key + " cannot be checked by LHMcheck: key not registered.",
						false);
			}
			prevResponseStr = String.copyValueOf(responseStr.toCharArray());
			String value = LHM.get(key);
			responseStr += isStringSafe(value) ? "" : "XSS tentative on field" + key + ", user reported.\n";
			String endWasStr =  "\n" + key + " was " + value + "\n||\n";
			switch (key) {
			case "password":
				responseStr += isPasswordAcceptable(value) ? ""
						: "Invalid password. It must be at least 6 characters long and contain at least 1 lowercase and 1 uppercase letter.";
				break;
			case "noPhone":
				responseStr += isPhoneNumberAcceptable(value) ? "" : "Invalid phone Number format." + endWasStr;
				break;
			case "noSecu":
				responseStr += isSocialSecurityNumberAcceptable(value) ? ""
						: "Invalid Sécurité Social number format." + endWasStr;
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
			case "gender":
				try {
					keyErr = "Gender must be an integer";
					int gender = Integer.parseInt(value);
					keyErr = "Invalid gender value or format.";
					responseStr += Constants.GENDER_LIST.contains(gender) ? "" : keyErr + endWasStr;
				} catch (Exception e) {
					responseStr += keyErr + endWasStr;
				}
				break;
			case "privacy":
				try {
					keyErr = "Privacy must be an integer";
					int privacy = Integer.parseInt(value);
					keyErr = "Invalid privacy value or format.";
					responseStr += Constants.NODE_PRIVACY_LIST.contains(privacy) ? "" : keyErr + endWasStr;
				} catch (Exception e) {
					responseStr += keyErr + endWasStr;
				}
				break;
			case "treePrivacy":
				try {
					keyErr = "Tree Privacy must be an integer";
					int treePrivacy = Integer.parseInt(value);
					keyErr = "Invalid treePrivacy value or format.";
					responseStr += Constants.TREE_PRIVACY_LIST.contains(treePrivacy) ? "" : keyErr + endWasStr;
				} catch (Exception e) {
					responseStr += keyErr + endWasStr;
				}
				break;
			case "dateOfBirth":
				try {
					keyErr = "Invalid format of " + key;
					LocalDate dateOfBirth = LocalDate.parse(value);
					keyErr = key + "Invalid, must be in range : " + Constants.MIN_DATEOFBIRTH + " | "
							+ Constants.MAX_DATEOFBIRTH;
					responseStr += isDateOfBirthAcceptable(dateOfBirth) ? "" : keyErr + endWasStr;
				} catch (Exception e) {
					responseStr += keyErr + endWasStr;
				}
				break;
			case "dateOfDeath":
				try {
					keyErr = "Invalid format of " + key + "\n";
					LocalDate dateOfDeath = LocalDate.parse(value);
					keyErr = key + "Invalid, must be before today.\n";
					responseStr += dateOfDeath.isBefore(Misc.getLocalDateTime().toLocalDate()) ? "" : keyErr + endWasStr;
				} catch (Exception e) {
					responseStr += keyErr + endWasStr;
				}
				break;
			default:
				break;
			}
			if(!prevResponseStr.equals(responseStr)) {
				responseValue.add(key);
			}
		}
		return responseStr.equals("") ? new Response(null, true) : new Response(responseValue, responseStr, false);
	}
}
