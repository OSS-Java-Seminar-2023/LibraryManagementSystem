package org.oss.LibraryManagementSystem.utils;

import java.sql.Timestamp;

public class Validator {
    public static boolean isStringNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }
}
