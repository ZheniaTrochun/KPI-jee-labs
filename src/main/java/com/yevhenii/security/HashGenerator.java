package com.yevhenii.security;

import javax.ejb.Stateless;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Stateless
public class HashGenerator {

    private String algo = "SHA-256";

    public HashGenerator() {
    }

    public HashGenerator(String algo) {
        this.algo = algo;
    }

    public String getHashedText(String text) {

        try {
            MessageDigest digest = MessageDigest.getInstance(algo);
            byte[] hash = digest.digest(text.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
