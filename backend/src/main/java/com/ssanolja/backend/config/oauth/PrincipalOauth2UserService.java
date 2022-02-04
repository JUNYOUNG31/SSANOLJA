package com.ssanolja.backend.config.oauth;

import com.ssanolja.backend.config.auth.PrincipalDetails;
import com.ssanolja.backend.db.entity.User;
import com.ssanolja.backend.db.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        System.out.println("getClientRegistration: " + userRequest.getClientRegistration());
//        System.out.println("getAccessToken: " + userRequest.getAccessToken().getTokenValue());

        OAuth2User oAuth2User = super.loadUser(userRequest);
//        System.out.println("getAttributes: " + oAuth2User.getAttributes());

        String userName = oAuth2User.getAttribute("name");
        String userEmail = oAuth2User.getAttribute("email");
        String provider = userRequest.getClientRegistration().getRegistrationId(); //google
        String providerId = oAuth2User.getAttribute("sub");
        String userNickname = provider + "_" + providerId; // google_0000000000

        User user = userRepository.findByUserEmail(userEmail);
        if(user == null) {
            user = User.builder()
                    .userName(userName)
                    .userEmail(userEmail)
                    .userNickname(userNickname)
                    .build();
            userRepository.save(user);
        } else {
            System.out.println("이미 구글 회원가입 완료 되어 있음");
        }
        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
