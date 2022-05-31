package com.converter.converter.auth.validation;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.StringJoiner;

@Component
public class CustomUsersValidator implements Validator {
    private final UserService userService;
    private final Validator basicValidator;
    private final Logger logger = LoggerFactory.getLogger(CustomUsersValidator.class);


    @Autowired
    public CustomUsersValidator(UserService userService, Validator basicValidator) {
        this.userService = userService;
        this.basicValidator = basicValidator;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Users.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        logger.info("Start");
        Users user = (Users) target;
        if (!user.getLogin().isEmpty() && userService.findUserByLogin(user.getLogin()).getLogin().equalsIgnoreCase(user.getLogin())) {
            errors.rejectValue("login", "Not unique user!", "This user exists already!");
            logger.error("This user exists already!");
        }
        if (!user.getMail().isEmpty() && userService.findUserByMail(user.getMail()).getMail().equalsIgnoreCase(user.getMail())) {
            errors.rejectValue("mail", "Not unique user!", "This user exists already!");
            logger.error("This user exists already!");
        }
        if (!user.getPhone().isEmpty() && userService.findUserByPhone(user.getPhone()).getPhone().equalsIgnoreCase(user.getPhone())) {
            errors.rejectValue("phone", "Not unique user!", "This user exists already!");
            logger.error("This user exists already!");
        }
        StringJoiner sje = new StringJoiner(",");
        sje.add(user.getName()).add(user.getSurname()).add(user.getPatronymic());
        if (!user.getName().isEmpty() && !user.getSurname().isEmpty())
            logger.info("End");
    }
}
