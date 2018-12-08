package com.doraro.permission_fast.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class EncptyUtils {

    public static String formPwdToDb(String password, String salt) {
        return new Sha256Hash(password,salt).toString();
    }
    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
    public static void main(String[] args) {
        System.out.println(formPwdToDb("root", "salt"));
    }

    public static String createSalt(int len) {
        return RandomStringUtils.randomAlphabetic(20);
    }
}
