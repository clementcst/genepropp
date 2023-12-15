package com.acfjj.app.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
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
	
	public static boolean isOneMonthOld(LocalDateTime dateTime) {
        long differenceInMonths = ChronoUnit.MONTHS.between(dateTime, getLocalDateTime());
        return differenceInMonths >= 1;
    }
}
