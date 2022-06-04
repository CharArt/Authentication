package com.converter.converter.auth.tools;

import com.converter.converter.auth.repository.dto.UserDTO;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;

@Component
public class UserDTOBuilder {

    private String login;
    private String name;
    private String surname;
    private String patronymic;
    private String password;
    private String gender;
    private String phone;
    private String mail;
    private Date birthday;
    private int age;
    private boolean enable;
    private Timestamp createdDate;

    public UserDTOBuilder() {
    }

    public UserDTOBuilder setLogin(String login) {
        this.login = login;
        return this;
    }

    public UserDTOBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public UserDTOBuilder setSurname(String surname) {
        this.surname = surname;
        return this;
    }

    public UserDTOBuilder setPatronymic(String patronymic) {
        this.patronymic = patronymic;
        return this;
    }

    public UserDTOBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public UserDTOBuilder setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public UserDTOBuilder setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserDTOBuilder setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public UserDTOBuilder setBirthday(Date birthday) {
        this.birthday = birthday;
        return this;
    }

    public UserDTOBuilder setAge(int age) {
        this.age = age;
        return this;
    }

    public UserDTOBuilder setEnable(boolean enable) {
        this.enable = enable;
        return this;
    }

    public UserDTOBuilder setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public UserDTO build() {
        UserDTO user = new UserDTO(this);
        return user;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    public Date getBirthday() {
        return birthday;
    }

    public int getAge() {
        return age;
    }

    public boolean isEnable() {
        return enable;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }
}