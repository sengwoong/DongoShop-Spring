package com.gangE.DongoShop.config;

import com.gangE.DongoShop.constants.SecurityConstants;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.repository.CustomerRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;


@Component
public class DongoUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
// 관리자 로그인
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Customer customer = customerRepository.findByEmail(username);
        System.out.println("customer");
        System.out.println("customer");
        System.out.println(customer);
        if (customer != null) {
            if (passwordEncoder.matches(pwd, customer.getPwd())) {

                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities(customer));
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        } else {
            throw new BadCredentialsException("No user registered with this details!");
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

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
