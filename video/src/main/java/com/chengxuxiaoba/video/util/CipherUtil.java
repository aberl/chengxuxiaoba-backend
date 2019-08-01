package com.chengxuxiaoba.video.util;

import org.springframework.util.DigestUtils;

import java.util.Base64;

public class CipherUtil {
    public static String generateMD5CipherText(String plainText) {
        if (plainText == null)
            return null;

        String md5Password = DigestUtils.md5DigestAsHex(plainText.getBytes());

        Base64.Encoder base64Encoder = Base64.getEncoder();

        md5Password = base64Encoder.encodeToString(md5Password.getBytes());

        return md5Password;
    }

    public static boolean matchMD5CipherText(String plainText, String matchCipherText) {
        if (plainText == null || matchCipherText == null)
            return false;

        String cipherText = generateMD5CipherText(plainText);

        return cipherText.equals(matchCipherText);
    }
}
