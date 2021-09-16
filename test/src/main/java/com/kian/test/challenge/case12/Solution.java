package com.kian.test.challenge.case12;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.NoSuchAlgorithmException;

public class Solution {

    public static void main(String[] args) throws NoSuchAlgorithmException {
        String hash = "35454B055CC325EA1AF2126E27707052";
        String password = "ILoveJava";

        String encHash = DigestUtils.md5Hex(password).toUpperCase();
        System.out.println("encHash = " + encHash);
    }
}
