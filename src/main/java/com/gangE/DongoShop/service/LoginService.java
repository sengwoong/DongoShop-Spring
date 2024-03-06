package com.gangE.DongoShop.service;


import com.gangE.DongoShop.constants.SecurityConstants;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Point;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PointRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import static com.gangE.DongoShop.util.Token.CreateToekn;

@Service
@Transactional
public class LoginService {


    private final CustomerRepository customerRepository;
    private final PointRepository pointRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public LoginService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,PointRepository pointRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.pointRepository = pointRepository;
    }


    public String loginUser(Customer authentication) {
        Customer customer = customerRepository.findByEmailWithAuthorities(authentication.getEmail());

        if (customer != null && passwordEncoder.matches(authentication.getPwd(),customer.getPwd())) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = CreateToekn(customer, key);
            return jwt;

        }
        return null;
    }




    public ResponseEntity<String> registerUser(Customer customer) {
        Customer savedCustomer = null;
        ResponseEntity response = null;
        Point point = new Point();
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            customerRepository.save(customer);

            point.setUser(customer);
            point.setPoint(0); // 초기 포인트를 0으로 설정
            pointRepository.save(point);

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


}