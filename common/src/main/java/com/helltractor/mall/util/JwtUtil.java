package com.helltractor.mall.util;

import com.helltractor.mall.constant.JwtConstant;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    
    private static final SecretKey SIGNING_KEY = Jwts.SIG.HS256.key().build();
    
    private static Date generateExp() {
        return generateExp(System.currentTimeMillis());
    }
    
    private static Date generateExp(long currentTimeMillis) {
        return new Date(currentTimeMillis + JwtConstant.TTL);
    }
    
    public static String createJWT(Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .expiration(generateExp())
                .signWith(SIGNING_KEY)
                .compact();
    }
    
    public static String createJWT(Claims claims) {
        return Jwts.builder()
                .claims(claims)
                .expiration(generateExp())
                .signWith(SIGNING_KEY)
                .compact();
    }
    
    public static String createJWT(Map<String, Object> claims, long currentTimeMillis) {
        return Jwts.builder()
                .claims(claims)
                .expiration(generateExp(currentTimeMillis))
                .signWith(SIGNING_KEY)
                .compact();
    }
    
    public static Jws<Claims> parseJWT(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(SIGNING_KEY)
                .build()
                .parseSignedClaims(token);
    }
    
    public static boolean verifyJWTExp(String token) {
        try {
            Claims claims = parseJWT(token).getPayload();
            Date exp = claims.getExpiration();
            return exp.after(new Date());
        } catch (JwtException e) {
            return false;
        }
    }
    
    public static String updateJWTExp(String token) throws JwtException {
        Claims claims = parseJWT(token).getPayload();
        return createJWT(claims);
    }
    
}