package com.converter.converter.auth.validation;

import com.converter.converter.auth.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
public class ToolsForValidatorImpl implements ToolsForValidator {
    private final Logger logger = LoggerFactory.getLogger(ToolsForValidatorImpl.class);

    @Override
    public boolean checkLogin(String login, Errors errors, UserService service) {
        logger.info("Start checkLogin");
        if (!login.isEmpty()) {
            if (!service.findUserByLogin(login).isEmpty()) {
                logger.error("This login use already!");
                errors.rejectValue("login", "Not unique login!", "This login use already!");
                logger.info("End checkLogin");
                return false;
            }
            logger.info("This new login!");
            logger.info("End checkLogin");
            return true;
        }
        logger.error("This login is empty!");
        errors.rejectValue("login", "This login is empty!", "This login is empty!");
        logger.info("End checkLogin");
        return false;
    }

    @Override
    public boolean checkEmail(String email, Errors errors, UserService service) {
        logger.info("Start checkEmail");
        if (!email.isEmpty()) {
            if (!service.findUserByMail(email).isEmpty()) {
                logger.error("This email use already!");
                errors.rejectValue("mail", "Not unique email!", "This email use already!");
                logger.info("End checkEmail");
                return false;
            }
            logger.info("This new email!");
            logger.info("End checkEmail");
            return true;
        }
        logger.error("This email is empty!");
        errors.rejectValue("mail", "This email is empty!", "This email is empty!");
        logger.info("End checkEmail");
        return false;
    }

    @Override
    public boolean checkPhone(String phone, Errors errors, UserService service) {
        logger.info("Start checkPhone");
        if (!phone.isEmpty()) {
            if (!service.findUserByPhone(phone).isEmpty()) {
                logger.error("This phone use already!");
                errors.rejectValue("phone", "Not unique phone!", "This phone use already!");
                logger.info("End checkPhone");
                return false;
            }
            logger.info("This new phone!");
            logger.info("End checkPhone");
            return true;
        }
        logger.error("This phone is empty!");
        errors.rejectValue("phone", "This phone is empty!", "This phone is empty!");
        logger.info("End checkPhone");
        return false;
    }
}
