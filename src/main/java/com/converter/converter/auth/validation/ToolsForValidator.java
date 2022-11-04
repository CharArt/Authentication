package com.converter.converter.auth.validation;

import com.converter.converter.auth.service.UserService;
import org.springframework.validation.Errors;

public interface ToolsForValidator {
    boolean checkLogin(String login, Errors errors, UserService service);

    boolean checkEmail(String email, Errors errors, UserService service);

    boolean checkPhone(String phone, Errors errors, UserService service);
}
