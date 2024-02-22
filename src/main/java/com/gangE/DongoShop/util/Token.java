package com.gangE.DongoShop.util;

import com.gangE.DongoShop.constants.SecurityConstants;
import com.gangE.DongoShop.model.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class Token {
    public static String CreateToekn(Customer customer, SecretKey key) {
        String jwt = Jwts.builder()
                .setIssuer("Dongo")
                .setSubject("JWT Token")
                .claim("username", customer.getName())
                .claim("authorities", customer.getAuthorities())
                .setIssuedAt(new java.util.Date())
                .setExpiration(new java.util.Date((System.currentTimeMillis() + 30000000)))
                .signWith(key).compact();
        return jwt;
    }


    // JWT를 파싱하고 클레임을 반환
    public static Claims parseToken(String token) {

        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        return    Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }

    // 서명 검증 및 JWT를 파싱하고 클레임을 반환


    // JWT의 만료 여부 확인
    public static boolean isTokenExpired(Claims claims) {
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }

}
