package com.converter.converter.auth.service;

import com.converter.converter.auth.entity.GoogleUser;
import com.converter.converter.auth.repository.GoogleUserRepository;
import com.converter.converter.auth.repository.dto.OAuth2UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@Transactional
public class GoogleUsersServiceImpl extends DefaultOAuth2UserService implements GoogleUsersService {

    private final GoogleUserRepository repository;
    private final UserService service;

    private final Logger logger = LoggerFactory.getLogger(GoogleUsersServiceImpl.class);

    @Autowired
    public GoogleUsersServiceImpl(GoogleUserRepository repository, UserService service) {
        this.repository = repository;
        this.service = service;
    }

    @Override
    public GoogleUser findGoogleUserById(Long id) {
        logger.info("Start_Method_findUserById(" + id + ")");
        return repository.findGoogleUserById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public GoogleUser findUserBySub(String sub) {
        logger.info("Start_Method_findUserBySub(" + sub + ")");
        return repository.findGoogleUserBySub(sub).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public GoogleUser findUserByNameAndSurnameAndEmail(String name, String surname, String email) {
        logger.info("Start_Method_findUserByNameAndSurnameAndEmail(" + name + ", " + surname + ", " + email + ")");
        return repository.findGoogleUserByNameAndSurnameAndEmail(name, surname, email).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public void deleteGoogleUserById(Long id) {
        logger.info("Start_Method_deleteGoogleUserById(" + id + ")");
        if (repository.findGoogleUserById(id).isPresent()) {
            repository.deleteGoogleUserById(id);
        }
    }

    @Override
    public boolean saveNewGoogleUser(OAuth2User oAuth2User) {
        if (!Objects.equals(oAuth2User, null)) {
            if (repository.findGoogleUserByNameAndSurnameAndEmail(oAuth2User.getAttribute("given_name"),
                    oAuth2User.getAttribute("family_name"),
                    oAuth2User.getAttribute("email")).isPresent()) {

                Long id = service.findUserByMail(oAuth2User.getAttribute("email")).getId();

                logger.info("Start_Method_saveNewGoogleUser(" + id.toString() + ", "
                        + oAuth2User.getAttribute("sub") + ", "
                        + oAuth2User.getAttribute("given_name") + ", "
                        + oAuth2User.getAttribute("family_name") + ", "
                        + oAuth2User.getAttribute("email") + ", "
                        + oAuth2User.getAttribute("email_verified") + ", "
                        + oAuth2User.getAttribute("locale") + ")");

                repository.saveNewGoogleUsers(id,
                        oAuth2User.getAttribute("sub"),
                        oAuth2User.getAttribute("given_name"),
                        oAuth2User.getAttribute("family_name"),
                        oAuth2User.getAttribute("email"),
                        oAuth2User.getAttribute("email_verified"),
                        oAuth2User.getAttribute("locale"));
                return true;
            } else {
                logger.info("client_is_exist");
                return false;
            }
        } else {
            logger.info("can't_use_null_entity");
            throw new NullPointerException();
        }
    }

    @Override
    public void saveRolesForGoogleUsers(Long google_id, Long roles_id) {
        logger.info("Start_Method_deleteGoogleUserById(" + google_id.toString() + ", " + roles_id.toString() + ")");
        repository.saveRoleForGoogleUser(google_id, roles_id);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        return new OAuth2UserDTO(oAuth2User);
    }
}