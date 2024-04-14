package com.gangE.DongoShop.filter;

import com.gangE.DongoShop.constants.SecurityConstants;
import com.gangE.DongoShop.dto.UserToken;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.repository.PointRepository;
import com.gangE.DongoShop.util.Token;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import static com.gangE.DongoShop.util.Token.CreateToekn;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final CustomerRepository customerRepository;



    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomOAuth2User oAuth2User  =(CustomOAuth2User) authentication.getPrincipal();
        System.out.println(oAuth2User);
        System.out.println(oAuth2User);
        System.out.println(oAuth2User);
        System.out.println(oAuth2User);

        Customer customer = customerRepository.findByNameWithAuthorities(oAuth2User.getName());



        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));




        String jwt = CreateToekn(customer, key);



        //28분
        response.sendRedirect("http://localhost:5173/auth/oauth-repsonse/" + jwt + "/8080");

    }


}
