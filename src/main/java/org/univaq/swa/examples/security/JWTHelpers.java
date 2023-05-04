package org.univaq.swa.examples.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 *
 * Una classe di utilità per provare i token JWT
 *
 */
public class JWTHelpers {

    private static JWTHelpers instance = null;
    private SecretKey jwtKey = null;

    private JWTHelpers() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("HmacSha256");
            jwtKey = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AuthenticationRes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public SecretKey getJwtKey() {
        return jwtKey;
    }

    public String validateToken(String token) {
        Jws<Claims> jwsc = Jwts.parserBuilder().setSigningKey(getJwtKey()).build().parseClaimsJws(token);
        return jwsc.getBody().getSubject();
    }

    public static JWTHelpers getInstance() {
        if (instance == null) {
            instance = new JWTHelpers();
        }
        return instance;
    }
}
