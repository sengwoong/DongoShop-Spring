package com.gangE.DongoShop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gangE.DongoShop.model.Customer;
import com.gangE.DongoShop.repository.CustomerRepository;
import com.gangE.DongoShop.filter.CustomOAuth2User;
import com.gangE.DongoShop.util.CreateAt;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {


//    private final LoginService loginService;

//    public OAuth2UserServiceImplement(LoginService loginService) {
//        this.loginService = loginService;
//    }
    private final CustomerRepository customerRepository;



    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();


        try {
            System.out.println(new ObjectMapper().writeValueAsString (oauth2User.getAttributes())); }
        catch (Exception exception) {
            exception.printStackTrace();
        }

        System.out.println(oauthClientName);
        System.out.println(oauthClientName);
        System.out.println(oauthClientName);

        Customer customer = null;

        String userId;
        String email;
        String type;

        if ("kakao".equals(oauthClientName)) {
            userId = (String) oauth2User.getAttribute("id");
            email = (String) oauth2User.getAttribute("email");
            type = "kakao";
        } else if ("naver".equals(oauthClientName)) {
            Map<String, String> responseMap = (Map<String, String>) oauth2User.getAttribute("response");
            userId = responseMap.get("id").substring(0, 14);
            email = responseMap.get("email");
            type = "naver";
        } else {
            // 다른 OAuth2 공급자의 처리 로직
            return oauth2User;
        }

        Customer userEntity = new Customer(userId, email, null,"oauth","nomal", new CreateAt().CreateAt());
        Customer existingUser = customerRepository.findByEmail(email);

        if (existingUser != null) {
            // 이미 사용자가 존재하면 패스합니다.
            System.out.println("해당 이메일 주소로 이미 등록된 사용자가 있습니다.");
        } else {
            // 사용자를 생성하여 저장합니다.

            customerRepository.save(userEntity);
            System.out.println("사용자가 성공적으로 저장되었습니다.");
        }



        return new CustomOAuth2User(userId);
    }
}