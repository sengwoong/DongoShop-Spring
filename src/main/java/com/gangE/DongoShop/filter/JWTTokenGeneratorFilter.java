package com.gangE.DongoShop.filter;


import com.gangE.DongoShop.constants.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class JWTTokenGeneratorFilter extends OncePerRequestFilter {

    // 토큰을 발급
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {

        System.out.println("로직실");

        System.out.println("로직실");
        System.out.println("로직실");
        System.out.println("로직실");
        System.out.println(SecurityContextHolder.getContext());
        Object SecurityContextHolder2=SecurityContextHolder.getContext();
        System.out.println("SecurityContextHolder2");
        System.out.println(SecurityContextHolder2);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object b=SecurityContextHolder.getContext().getAuthentication();;


        if (null == authentication) {

            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer("Eazy Bank").setSubject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 30000000))
                    .signWith(key).compact();


            response.setHeader(SecurityConstants.JWT_HEADER, jwt);


        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        System.out.println(request.getServletPath());
        Object a = request.getServletPath();
        System.out.println(a);
        Object b = request.getServletPath().equals("/user");
        System.out.println(request.getServletPath().equals("/user"));


        return !request.getServletPath().equals("/user");
    }

    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
        Set<String> authoritiesSet = new HashSet<>();
        for (GrantedAuthority authority : collection) {
            authoritiesSet.add(authority.getAuthority());
        }
        return String.join(",", authoritiesSet);
    }

}
