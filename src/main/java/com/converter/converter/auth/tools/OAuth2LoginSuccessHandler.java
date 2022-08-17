package com.converter.converter.auth.tools;

import com.converter.converter.auth.entity.GoogleUser;
import com.converter.converter.auth.entity.Roles;
import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.jwt.JwtTokenProvider;
import com.converter.converter.auth.repository.dto.OAuth2UserImpl;
import com.converter.converter.auth.service.GoogleUsersServiceImpl;
import com.converter.converter.auth.service.RoleService;
import com.converter.converter.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final RoleService roleService;
    private final Logger logger = LoggerFactory.getLogger(OAuth2LoginSuccessHandler.class);

    @Autowired
    public OAuth2LoginSuccessHandler(GoogleUsersServiceImpl googleService,
                                     UserService userService,
                                     RoleService roleService) {
        this.googleService = googleService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        OAuth2UserImpl oAuth2User = (OAuth2UserImpl) authentication.getPrincipal();

        if (!oAuth2User.getEmail().isEmpty()) {

            Users users = userService.findUserByMail(oAuth2User.getEmail());
            GoogleUser googleUser = googleService.findUserBySub(oAuth2User.getSub());

            if (!users.isEmpty() && googleUser.isEmpty()) {
                logger.info("User first time entered  with google email");

                googleService.saveNewGoogleUser(oAuth2User);

                for (Roles role : users.getRoles()) {
                    googleService.saveRolesForGoogleUsers(googleService.findUserBySub(oAuth2User.getSub()).getId(), role.getId());
                }
            }

            if (users.isEmpty() && googleUser.isEmpty()) {
                logger.info("User first time become used our site");

                Users user = new Users();
                user.setLogin(oAuth2User.getGiven_name() + oAuth2User.getFamily_name() + oAuth2User.getSub().substring(0, 2));
                user.setMail(oAuth2User.getEmail());
                user.setName(oAuth2User.getGiven_name());
                user.setSurname(oAuth2User.getFamily_name());
                user.setRoles(roleService.getDefaultRole());
                userService.saveNewUser(users);

                googleService.saveNewGoogleUser(oAuth2User);

                for (Roles role : users.getRoles()) {
                    googleService.saveRolesForGoogleUsers(googleService.findUserBySub(oAuth2User.getSub()).getId(), role.getId());
                }
            }

            if (!users.isEmpty() && !googleUser.isEmpty()) {
                logger.info("user already exists!");
                System.out.println("user already exists!");
            }
        }

        oAuth2User.setGoogleUser(googleService.findUserBySub(oAuth2User.getSub()));

        response.sendRedirect("/hello");

        JwtTokenProvider provider = new JwtTokenProvider(userService);
        provider.createToken();

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
