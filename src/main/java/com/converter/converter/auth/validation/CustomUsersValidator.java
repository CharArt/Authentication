package com.converter.converter.auth.validation;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.entity.repository.dto.UserDTO;
import com.converter.converter.auth.service.MailSender;
import com.converter.converter.auth.service.UserService;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


@Component
public class CustomUsersValidator implements Validator {
    private final UserService userService;
    private final MailSender mailSender;
    private final ToolsForValidator validator;

    private final Logger logger = LoggerFactory.getLogger(CustomUsersValidator.class);


    @Autowired
    public CustomUsersValidator(UserService userService, MailSender mailSender, ToolsForValidator validator) {
        this.userService = userService;
        this.mailSender = mailSender;
        this.validator = validator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        if (UserDTO.class.equals(aClass)) return true;
        if (User.class.equals(aClass)) return true;
//        return Users.class.equals(aClass);
        return false;
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.info("Start user validator");
        Users user = new Users();
        if (target instanceof UserDTO) {
            UserDTO userDTO = (UserDTO) target;
            user = new Users(userDTO);
        } else {
            user = (Users) target;
        }
        validator.checkLogin(user.getLogin(), errors, userService);
        if (validator.checkEmail(user.getMail(), errors, userService)) {
            String message = String.format("Hello, %s Welcome to Currency converter. Please, visit next link: http://localhost:8080/activated/%s",
                    user.getLogin(),
                    user.getActivated()
            );
            mailSender.send(user.getMail(), "Activation code", message);
        }
        validator.checkPhone(user.getPhone(), errors, userService);
        logger.info("End user validator");
    }

}
