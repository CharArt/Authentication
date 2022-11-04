package com.converter.converter.auth.service;

import com.converter.converter.auth.entity.GoogleUser;
import com.converter.converter.auth.entity.repository.GoogleUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;

@Service
@Transactional
public class GoogleUserServiceImpl implements GoogleUsersService {

    private final GoogleUserRepository repository;
    private final UserService service;

    private final Logger logger = LoggerFactory.getLogger(GoogleUserServiceImpl.class);

    @Autowired
    public GoogleUserServiceImpl(GoogleUserRepository repository, UserService service) {
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
        return repository.findGoogleUserBySub(sub).orElse(new GoogleUser());
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
                    oAuth2User.getAttribute("email")).isEmpty()) {

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
}