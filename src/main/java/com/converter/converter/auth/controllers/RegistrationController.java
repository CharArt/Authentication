package com.converter.converter.auth.controllers;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.entity.repository.dto.UserDTO;
import com.converter.converter.auth.service.RoleService;
import com.converter.converter.auth.service.UserService;
import com.converter.converter.auth.validation.CustomUsersValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final RoleService roleService;
    private final CustomUsersValidator myCustomUserValidation;

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    public RegistrationController(UserService userService,
                                  RoleService roleService,
                                  CustomUsersValidator myCustomUserValidation) {
        this.userService = userService;
        this.roleService = roleService;
        this.myCustomUserValidation = myCustomUserValidation;
    }

    @InitBinder
    protected void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.addValidators(myCustomUserValidation);
    }

    @GetMapping("/registration")
    public String FrontPage(ModelMap modelMap) {
        UserDTO newUser = new UserDTO();
        modelMap.addAttribute("user", newUser);
        return "registration";
    }

    @PostMapping("/registration")
    public String checkUserInfo(@ModelAttribute("user") UserDTO user, BindingResult bindingResult, ModelMap modelMap) {
        logger.info("-Start_Method_checkUserInfo");
        myCustomUserValidation.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        Users users = new Users(user);
        users.setActivated(UUID.randomUUID().toString());
        users.setRoles(roleService.getDefaultRole());
        userService.saveNewUser(users);
        userService.saveRoleForUser(users);
        String url = String.format("redirect:/login/%s", users.getActivated());
        return url;
    }

    @GetMapping("activated/{code}")
    public String activate(ModelMap modelMap, @PathVariable String code) {
        Users users = userService.activated(code);
        UserDTO userDTO = new UserDTO(users);
        modelMap.addAttribute("user", userDTO);
        users.setEnable(true);
        userService.updateUser(users);
        return "activated";
    }



}
