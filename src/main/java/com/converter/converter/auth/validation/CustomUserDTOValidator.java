package com.converter.converter.auth.validation;

import com.converter.converter.auth.entity.repository.dto.UserDTO;
import com.converter.converter.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.StringJoiner;

@Component
public class CustomUserDTOValidator implements Validator {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(CustomUsersValidator.class);

    @Autowired
    public CustomUserDTOValidator(UserService userService) {
        this.userService = userService;
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
            errors.rejectValue("login", "Not unique user!", "This user exists already!");
            logger.error("This user exists already!");
        }
        if (!user.getMail().isEmpty() && !userService.findUserByMail(user.getMail()).isEmpty()) {
            errors.rejectValue("mail", "Not unique user!", "This user exists already!");
            logger.error("This user exists already!");
        }
        if (!user.getPhone().isEmpty() && !userService.findUserByPhone(user.getPhone()).isEmpty()) {
            errors.rejectValue("phone", "Not unique user!", "This user exists already!");
            logger.error("This user exists already!");
        }
        StringJoiner sje = new StringJoiner(",");
        sje.add(user.getName()).add(user.getSurname()).add(user.getPatronymic());
        if (!user.getName().isEmpty() && !user.getSurname().isEmpty())
            logger.info("End user validator");
    }
}
