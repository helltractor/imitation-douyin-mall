package com.helltractor.mall.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    
    /**
     * entry password
     */
    public static String encryptPassword(String rawPassword) {
        return encoder.encode(rawPassword);
    }
    
    /**
     * compare password
     */
    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }
    
}