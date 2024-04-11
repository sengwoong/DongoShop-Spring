package com.gangE.DongoShop.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {


    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(request);
        String oauthClientName = request.getClientRegistration().getClientName();


        try {
            System.out.println(new ObjectMapper().writeValueAsString (oauth2User.getAttributes())); }
        catch (Exception exception) {
            exception.printStackTrace();
        }




        return oauth2User;
    }
}