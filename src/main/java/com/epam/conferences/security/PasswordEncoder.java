package com.epam.conferences.security;

import com.epam.conferences.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class PasswordEncoder {
    private static final Logger logger = LogManager.getLogger(PasswordEncoder.class);

    private PasswordEncoder() {
    }

    public static char[] encryptPassword(char[] password) throws ServiceException {
        return bytesToHex(encrypt(Arrays.toString(password)));
    }

    private static byte[] encrypt(String password) throws ServiceException {
        logger.info("PasswordEncoder: start encoding");
        if (password == null) {
            logger.error("PasswordEncoder: password can`t be empty!");
            throw new ServiceException("PasswordEncoder: password can`t be empty!");
        }
        byte[] bytes = password.getBytes();
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            logger.error("PasswordEncoder: error during encrypting password {}", e.getMessage());
            throw new ServiceException(e);
        }
        return messageDigest.digest(bytes);
    }

    private static char[] bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder(2 * bytes.length);
        for (byte aByte : bytes) {
            String hex = Integer.toHexString(0xff & aByte);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString().toCharArray();
    }


}
