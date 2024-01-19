package com.gangE.DongoShop.util;

import com.gangE.DongoShop.model.Customer;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

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
    }}
