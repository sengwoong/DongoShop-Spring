package com.gangE.DongoShop.service;


import com.gangE.DongoShop.constants.SecurityConstants;
import com.gangE.DongoShop.dto.EmailAndPwd;
import com.gangE.DongoShop.dto.UserToken;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.model.Point;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PointRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

import static com.gangE.DongoShop.util.Token.CreateToekn;

@Service
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


    public UserToken loginUser(EmailAndPwd authentication) {
        Customer customer = customerRepository.findByEmailWithAuthorities(authentication.getEmail());

        if (customer != null && passwordEncoder.matches(authentication.getPwd(),customer.getPwd())) {
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = CreateToekn(customer, key);
            return new UserToken((long)customer.getId(), jwt);

        }
        return null;
    }


    public UserToken registerUser(Customer customer) {
        Customer isDuplication = customerRepository.findByName(customer.getName());
        if(isDuplication != null){
            return null;
        }
        EmailAndPwd authentication = new EmailAndPwd(customer.getEmail(), customer.getPwd());

        Point point = new Point();
        try {
            String hashPwd = passwordEncoder.encode(customer.getPwd());
            customer.setPwd(hashPwd);
            customer.setCreateDt(String.valueOf(new Date(System.currentTimeMillis())));
            customerRepository.save(customer);

            point.setUser(customer);
            point.setPoint(0); // 초기 포인트를 0으로 설정
            pointRepository.save(point);
            UserToken token = loginUser(authentication);
           return token;

        } catch (Exception ex) {

        }

        return null;
    }

}