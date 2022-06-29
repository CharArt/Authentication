package com.converter.converter.auth.service;

import com.converter.converter.auth.entity.Users;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public interface UserService extends UserDetailsService {

    Users findUserById(Long userId);

    Users findUserByLogin(String login);

    List<Users> findUserByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);

    Users findUserByMail(String mail);

    List<Users> findUserByGender(String gender);

    Users findUserByPhone(String phone);

    List<Users> findUserByBirthday(Date birthday);

    List<Users> findUserByAge(int age);

    List<Users> findUserByEnable(boolean enable);

    List<Users> findUserByCreated(Timestamp created);

    List<Users> findAll();

    Users getLastPerson();

    void create(Users user);

    void deleteUserByIdAndLogin(Long id, String login);

    void updateUser(Users user, Long id);

    void saveNewUser(Users users);

    void saveRoleForUser(Users users);
}