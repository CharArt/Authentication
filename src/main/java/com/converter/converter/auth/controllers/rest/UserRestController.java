package com.converter.converter.auth.controllers.rest;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.repository.dto.UserDTO;
import com.converter.converter.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long id) {
        UserDTO userDTO = new UserDTO(userService.findUserById(id));
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAllUserOrByName(@RequestParam(required = false) String login) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        if (login != null) {
            UserDTO userDTO = new UserDTO(userService.findUserByLogin(login));
            userDTOList.add(userDTO);
        } else {
            list = userService.findAll();
            for (Users user : list) {
                UserDTO userDTO = new UserDTO(user);
                userDTOList.add(userDTO);
            }
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/NSP")
    public ResponseEntity<List<UserDTO>> findUsersByNameAndSurnameAndPatronymic(@RequestParam String name, @RequestParam String surname, @RequestParam String patronymic) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        list = userService.findAll();
        for (Users user : list) {
            UserDTO userDTO = new UserDTO(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("Email")
    public ResponseEntity<UserDTO> findUserByEmail(@RequestParam String email) {
//        Проверка на соответсвие pattern!
        UserDTO userDTO = new UserDTO(userService.findUserByMail(email));
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/Gender")
    public ResponseEntity<List<UserDTO>> findUsersByGender(@RequestParam String gender) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        //        Проверка на соответсвие pattern!
        list = userService.findUserByGender(gender);
        for (Users user : list) {
            UserDTO userDTO = new UserDTO(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/Phone")
    public ResponseEntity<UserDTO> findUsersByPhone(@RequestParam String phone) {
        //        Проверка на соответсвие pattern!
        UserDTO userDTO = new UserDTO(userService.findUserByPhone(phone));
        return new ResponseEntity<UserDTO>(userDTO, HttpStatus.OK);
    }

    @GetMapping("/Age")
    public ResponseEntity<List<UserDTO>> findUsersByAge(@RequestParam Integer age) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        //        Проверка на соответсвие pattern!
        list = userService.findUserByAge(age);
        for (Users user : list) {
            UserDTO userDTO = new UserDTO(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    @GetMapping("/Enable")
    public ResponseEntity<List<UserDTO>> findUsersByEnable(@RequestParam boolean enable) {
        List<Users> list = new ArrayList<>();
        List<UserDTO> userDTOList = new ArrayList<>();
        //        Проверка на соответсвие pattern!
        list = userService.findUserByEnable(enable);
        for (Users user : list) {
            UserDTO userDTO = new UserDTO(user);
            userDTOList.add(userDTO);
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }


}