package com.example.renthouseweb_be.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;
@Component
public class JwtTokenProvider {
    private final SecretKey key;
    public SecretKey getKey() {
        return key;
    }
    public JwtTokenProvider() {
        // Generate the SecretKey during construction
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
    public String generateToken(String username, long expirationMs, int isOwner, SecretKey secretKey) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setSubject(username)
                .claim("isOwner", isOwner)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    public boolean validateToken(String token, SecretKey secretKey) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    public int getIsOwnerFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
        return claims.get("isOwner", Integer.class);
    }
}
