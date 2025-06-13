package com.DemoRefri.demoRefri.utils;

import io.jsonwebtoken.security.Keys;
import java.util.Base64;
import javax.crypto.SecretKey;

public class KeyGenerator {
    public static void main(String[] args) {
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256); // ✅ Genera clave segura
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded()); // ✅ Convierte a Base64
        System.out.println("Clave Secreta Generada: " + encodedKey);
    }
}