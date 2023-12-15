package com.acfjj.app.utils;

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
}
