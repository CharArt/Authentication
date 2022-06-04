package com.converter.converter.auth.repository.dto;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.tools.UserDTOBuilder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.StringJoiner;

@Component
@Valid
public class UserDTO {

    private Long id;

    @NotNull
    @Max(30)
    private String login;

    @NotNull
    @Max(30)
    private String name;

    @NotNull
    @Max(30)
    private String surname;

    @Max(30)
    private String patronymic;

    @Max(100)
    private String password;

    @Max(6)
    private String gender;

    @Max(15)
    private String phone;

    @Max(50)
    @NotNull
    private String mail;

    @Past
    private Date birthday;

    private int age;

    private Boolean enable;

    private Timestamp createdDate;

    public UserDTO() {
    }

    public UserDTO(UserDTOBuilder user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't been empty!");
        }
        this.login = user.getLogin();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.patronymic = user.getPatronymic();
        this.password = user.getPassword();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.mail = user.getMail();
        this.birthday = user.getBirthday();
        this.age = user.getAge();
        this.enable = user.isEnable();
        this.createdDate = user.getCreatedDate();
    }

    public UserDTO(Users user) {
        if (user == null) {
            throw new IllegalArgumentException("User can't been empty!");
        }
        this.id = user.getId();
        this.login = user.getLogin();
        this.name = user.getName();
        this.surname = user.getSurname();
        this.patronymic = user.getPatronymic();
        this.password = user.getPassword();
        this.gender = user.getGender();
        this.phone = user.getPhone();
        this.mail = user.getMail();
        this.birthday = user.getBirthday();
        this.age = user.getAge();
        this.enable = user.getEnable();
        this.createdDate = user.getCreatedDate();
    }

    public Long getId() {
        return id;
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

    public Boolean getEnable() {
        return enable;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (patronymic != null ? patronymic.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Users)) return false;

        UserDTO user = (UserDTO) obj;

        if (!Objects.equals(login, user.login)) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        if (surname != null ? !surname.equals(user.surname) : user.surname != null) return false;
        if (patronymic != null ? !patronymic.equals(user.patronymic) : user.patronymic != null) return false;
        return this.hashCode() == user.hashCode();
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(" ,", "[ ", " ]");
        if (id != null){
            joiner.add(id.toString());
        }
        joiner.add(login)
                .add(name)
                .add(surname)
                .add(patronymic)
                .add(gender)
                .add(phone)
                .add(mail)
                .add(birthday.toString())
                .add(Integer.toString(age))
                .add(enable.toString())
                .add(createdDate.toString());
        return joiner.toString();
    }

    public boolean isEmpty() {
        return !this.login.isEmpty() || !this.name.isEmpty() || !this.surname.isEmpty() || !this.mail.isEmpty();
    }
}