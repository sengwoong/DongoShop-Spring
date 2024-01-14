package com.gangE.DongoShop.controller;


import com.gangE.DongoShop.constants.SecurityConstants;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.service.CustomerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody Customer customer) {
        Customer savedCustomer = null;
        ResponseEntity response = null;
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());

            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            customerService.saveCustomer(customer);

                response = ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body("Given user details are successfully registered");

        } catch (Exception ex) {
            response = ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An exception occured due to " + ex.getMessage());
        }
        return response;
    }

    @GetMapping("/user")
    public String loginUser(@RequestBody Customer authentication, HttpServletResponse response) {

        Customer customer = customerService.findCustomerByEmail(authentication.getEmail());
        if (customer != null && passwordEncoder.matches(authentication.getPwd(),customer.getPwd())) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            String jwt = Jwts.builder()
                    .setIssuer("Dongo")
                    .setSubject("JWT Token")
                    .claim("username", customer.getName())
                    .setIssuedAt(new java.util.Date())
                    .setExpiration(new java.util.Date((System.currentTimeMillis() + 30000000)))
                    .signWith(key).compact();

            response.setHeader(SecurityConstants.JWT_HEADER, jwt);

            return jwt;
        } else {
            return null;
        }
    }


}
