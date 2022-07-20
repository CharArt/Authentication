package com.converter.converter.auth.service;

import com.converter.converter.auth.entity.GoogleUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

public interface GoogleUsersService {

    public GoogleUser findGoogleUserById(Long id);

    public GoogleUser findUserBySub(String sub);

    public GoogleUser findUserByNameAndSurnameAndEmail(String name, String surname, String email);

    void deleteGoogleUserById(Long id);

    boolean saveNewGoogleUser(OAuth2User oAuth2User);

    void saveRolesForGoogleUsers(Long google_id, Long roles_id);
}
