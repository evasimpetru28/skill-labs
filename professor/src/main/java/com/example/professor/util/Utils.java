package com.example.professor.util;

import org.apache.commons.lang3.RandomStringUtils;

public class Utils {

	public static String getShortUUID() {
		return RandomStringUtils.randomAlphanumeric(10);
	}
}
