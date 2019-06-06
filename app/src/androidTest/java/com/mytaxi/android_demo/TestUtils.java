package com.mytaxi.android_demo;

import com.mytaxi.android_demo.models.Driver;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;

public class TestUtils {



    public static String calculateSHA256(final String password, final String salt) {
        String passwordWithSalt = password + salt;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(passwordWithSalt.getBytes(StandardCharsets.UTF_8));
            return String.format("%064x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return passwordWithSalt;
    }

    public static ArrayList<Driver> getDversList() {
        final ArrayList<Driver> drivers = new ArrayList<>();
        drivers.add(new Driver("Sarah Scott", "15279809827", "", "London", new Date()));
        return drivers;
    }
}
