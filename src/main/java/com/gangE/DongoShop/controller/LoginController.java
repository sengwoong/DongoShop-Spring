package com.gangE.DongoShop.controller;


import com.gangE.DongoShop.constants.SecurityConstants;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.service.PostService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static com.gangE.DongoShop.util.Token.CreateToekn;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private CustomerRepository customerRepository;


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
            customerRepository.save(customer);

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


        Customer customer = customerRepository.findByEmailWithAuthorities(authentication.getEmail());


        logger.info("Login customer aaaaa: {}", customer);

        if (customer != null && passwordEncoder.matches(authentication.getPwd(),customer.getPwd())) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));

            String jwt = CreateToekn(customer, key);

            new UsernamePasswordAuthenticationToken(customer.getName(), customer.getPwd(), getGrantedAuthorities(customer));




            return jwt;
        } else {
            return null;
        }


    }
    @Transactional
    private List<GrantedAuthority> getGrantedAuthorities(Customer customer) {
        // 패치 조인을 사용하여 authorities를 로딩
        Customer customerWithAuthorities = customerRepository.findByIdWithAuthorities(customer.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(customerWithAuthorities.getRole()));
        return grantedAuthorities;
    }


}