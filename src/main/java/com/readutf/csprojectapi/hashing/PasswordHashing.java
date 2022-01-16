package com.readutf.csprojectapi.hashing;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {


    MessageDigest messageDigest;

    public PasswordHashing() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("SHA-256");
    }

    public String generateHash(String toHash) {
        byte[] bytes = toHash.getBytes(StandardCharsets.UTF_8);
        return bytesToHex(messageDigest.digest(bytes));
    }

    public boolean isValidPassword(String password, String hash) {
        return generateHash(password).equalsIgnoreCase(hash);
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

}
