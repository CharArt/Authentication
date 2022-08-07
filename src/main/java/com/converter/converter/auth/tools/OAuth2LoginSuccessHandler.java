package com.converter.converter.auth.tools;

import com.converter.converter.auth.entity.GoogleUser;
import com.converter.converter.auth.entity.Roles;
import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.repository.dto.OAuth2UserImpl;
import com.converter.converter.auth.service.GoogleUsersServiceImpl;
import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final GoogleUsersServiceImpl googleService;
    private final UserService userService;

    @Autowired
    public OAuth2LoginSuccessHandler(GoogleUsersServiceImpl googleService, UserService userService) {
        this.googleService = googleService;
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2UserImpl oAuth2User = (OAuth2UserImpl) authentication.getPrincipal();

        if (userService.findUserByMail(oAuth2User.getSub()) == null) {
            if (googleService.saveNewGoogleUser(oAuth2User)) {
                Users users = userService.findUserByMail(oAuth2User.getAttribute("email"));
                GoogleUser googleUser = googleService.findUserBySub(oAuth2User.getAttribute("sub"));
                for (Roles role : users.getRoles()) {
                    googleService.saveRolesForGoogleUsers(googleUser.getId(), role.getId());
                }
            }
        }

        oAuth2User.setGoogleUser(googleService.findUserBySub(oAuth2User.getSub()));

        response.sendRedirect("/hello");
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
