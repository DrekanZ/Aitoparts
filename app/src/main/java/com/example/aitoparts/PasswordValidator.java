package com.example.aitoparts;

import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator{
    public static boolean isValid(String password) {
        // Check if password is at least 8 characters long
        if (password.length() < 8) {
            return false;
        }

        // Check if password contains at least one uppercase letter
        boolean hasUppercase = !password.equals(password.toLowerCase());
        if (!hasUppercase) {
            return false;
        }

        // Check if password contains at least one lowercase letter
        boolean hasLowercase = !password.equals(password.toUpperCase());
        if (!hasLowercase) {
            return false;
        }

        // Check if password contains at least one digit
        boolean hasDigit = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) {
                hasDigit = true;
                break;
            }
        }
        if (!hasDigit) {
            return false;
        }

        // Check if password contains at least one special character
        Pattern specialCharPattern = Pattern.compile("[!@#$%^&*()_+-=\\{\\}\\[\\]|;:\"',.<>?/]");
        Matcher matcher = specialCharPattern.matcher(password);
        boolean hasSpecialChar = matcher.find();
        if (!hasSpecialChar) {
            return false;
        }

        // Password is valid
        return true;
    }
}