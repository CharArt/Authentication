package com.converter.converter.auth.controllers;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.service.UserService;
import com.converter.converter.auth.validation.CustomUsersValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class RegistrationController {
    private UserService userService;
    private CustomUsersValidator myCustomUserValidation;

    @Autowired
    public RegistrationController(UserService userService, CustomUsersValidator myCustomUserValidation) {
        this.userService = userService;
        this.myCustomUserValidation = myCustomUserValidation;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.addValidators(myCustomUserValidation);
    }

    @GetMapping("/registration")
    public String FrontPage(ModelMap modelMap) {
        Users newUser = new Users();
        modelMap.addAttribute("user", newUser);
        return "registration";
    }

    @PostMapping("/registration")
    public String checkUserInfo(@ModelAttribute("user") Users user, BindingResult bindingResult) {
        myCustomUserValidation.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.create(user);
        return "redirect:/login";
    }
}
