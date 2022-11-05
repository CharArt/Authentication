package com.converter.converter.auth.validation;

import com.converter.converter.auth.entity.repository.dto.UserDTO;
import com.converter.converter.auth.service.MailSender;
import com.converter.converter.auth.service.UserService;
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
        logger.info("Start supports");
        if(UserDTO.class.equals(aClass)){
            logger.info("Finished well supports");
            return true;
        }
        else {
            logger.info("Finished bad supports");
            return false;
        }
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.info("Start user validator");
        UserDTO user = (UserDTO) target;
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
