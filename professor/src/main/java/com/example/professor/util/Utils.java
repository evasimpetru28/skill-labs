package com.example.professor.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {

	public static String getShortUUID() {
		return RandomStringUtils.randomAlphanumeric(10);
	}

	public static String localDateTimeToString(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern("dd.MM.yyyy (HH:mm)"));
	}
}
