package com.gangE.DongoShop.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

public class RequestValidationBeforeFilter implements Filter {

    private static final String SECRET_KEY = "YourSecretKeyHere";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String header = req.getHeader(AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7); // "Bearer " 이후의 부분을 토큰으로 간주
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(SECRET_KEY.getBytes(StandardCharsets.UTF_8))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                // 토큰의 만료 여부 확인
                if (claims.getExpiration().before(new java.util.Date())) {
                    throw new BadCredentialsException("Token expired");
                }

                // 기타 클레임 확인 등의 추가 검증 로직 추가 가능

            } catch (JwtException e) {
                throw new BadCredentialsException("Invalid token");
            }
        }
        chain.doFilter(request, response);
    }

    // 다른 메서드들은 삭제
}
