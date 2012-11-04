package org.happyfaces.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

/**
 * Password utility.
 * 
 * @author Ignas
 * 
 */
public final class PasswordUtils {

    /** Logger. */
    private static Logger logger = Logger.getLogger(PasswordUtils.class);

    /**
     * No need to create util instance.
     */
    private PasswordUtils() {

    }

    /**
     * Hash password.
     * 
     * @param password
     *            original password.
     * @return hashed password.
     */
    public static String hashPassword(String password) {
        if (password == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(password.getBytes("UTF-8"));
            byte rawBytes[] = messageDigest.digest();
            String hashedPassword = (new Base64()).encodeAsString(rawBytes);
            return hashedPassword;
        } catch (NoSuchAlgorithmException e) {
            logger.error("Password hashing  algorithm not found", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("UTF-8 encoding not supported", e);
        }
        return null;
    }

    /**
     * @return
     */
    // TODO implement
    public static String generateRandomPassword() {
        return "123456";
    }
}
