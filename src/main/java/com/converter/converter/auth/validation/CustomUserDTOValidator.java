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
public class CustomUserDTOValidator implements Validator {
    private final UserService userService;
    private final MailSender mailSender;
    private final Logger logger = LoggerFactory.getLogger(CustomUsersValidator.class);

    @Autowired
    public CustomUserDTOValidator(UserService userService, MailSender mailSender) {
        this.userService = userService;
        this.mailSender = mailSender;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.info("Start user validator");

        UserDTO user = (UserDTO) target;

        if (!user.getLogin().isEmpty() && !userService.findUserByLogin(user.getLogin()).isEmpty()) {
            errors.rejectValue("login", "Not unique login!", "This login use already!DTO");
            logger.error("This user exists already!");
        }

        if (!user.getMail().isEmpty() && !userService.findUserByMail(user.getMail()).isEmpty()) {
            errors.rejectValue("mail", "Not unique email!", "This email use already!DTO");
            logger.error("This user exists already!");
        } else {
            String message = String.format("Hello, %s \n" +
                            "Welcome to Currency converter. Please, visit next link: http://localhost:8080/registration/activated/%s",
                    user.getLogin(),
                    user.getActivated()
            );
            mailSender.send(user.getMail(), "Activation code", message);
        }

        if (!user.getPhone().isEmpty() && !userService.findUserByPhone(user.getPhone()).isEmpty()) {
            errors.rejectValue("phone", "Not unique phone!", "This phone use already! DTO");
            logger.error("This user exists already!");
        }
        logger.info("End user validator");
    }
}
