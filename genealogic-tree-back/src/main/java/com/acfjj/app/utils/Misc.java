package com.acfjj.app.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Misc {
	public static Integer findMaxFrequency(List<Integer> liste) {
        return liste.stream()
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
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
	
	 public static boolean parentIsOlder(String parentBirthDate, String childBirthDate) {
	        LocalDate parentDate = formatObjectToLocalDate(parentBirthDate);
	        LocalDate childDate = formatObjectToLocalDate(childBirthDate);

	        return childDate.isAfter(parentDate.plusYears(15));
	    }

	    public static boolean parentWasAlive(String parentDeathDate, String childBirthDate) {
	    	if(Objects.isNull(parentDeathDate)) {
	    		return true;
	    	}
	        LocalDate parentDate = formatObjectToLocalDate(parentDeathDate);
	        LocalDate childDate = formatObjectToLocalDate(childBirthDate);
	        if(Objects.isNull(parentDate)) {
	    		return true;
	    	}

	        return parentDate.isAfter(childDate);
	    }

	    public static boolean birthIsPossible(String birthDate, String deathDate) {
	    	if(Objects.isNull(deathDate)) {
	    		return true;
	    	}
	        LocalDate birth = formatObjectToLocalDate(birthDate);
	        LocalDate death = formatObjectToLocalDate(deathDate);
	        if(Objects.isNull(death)) {
	    		return true;
	    	}

	        return death.isAfter(birth);
	    }

	    public static boolean dateOfDeathIsCredible(String birthDate, String deathDate) {
	    	if(Objects.isNull(deathDate)) {
	    		return true;
	    	}
	        LocalDate birth = formatObjectToLocalDate(birthDate);
	        LocalDate death = formatObjectToLocalDate(deathDate);
	        if(Objects.isNull(death)) {
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
	    
	
	public static boolean isOneMonthOld(LocalDateTime dateTime) {
        long differenceInMonths = ChronoUnit.MONTHS.between(dateTime, getLocalDateTime());
        return differenceInMonths >= 1;
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
                return -10L;
            }
        }
    }
}
