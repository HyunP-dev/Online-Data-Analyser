package kr.ac.hallym.onlinedataanalyser.toolkit;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class Cryptography {
    public String SHA256 = "SHA-256";

    public String encrypt(String algorithm, String text) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(text.getBytes());
        byte[] encrypted = md.digest();

        StringBuilder builder = new StringBuilder();
        for (byte b : encrypted) builder.append(String.format("%02x", b));
        return builder.toString();
    }

    public static void main(String[] args) throws NoSuchAlgorithmException {
        System.out.println(Cryptography.encrypt(SHA256, "1234"));
    }
}
