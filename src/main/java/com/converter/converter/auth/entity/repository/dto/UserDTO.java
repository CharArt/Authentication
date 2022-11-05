package com.converter.converter.auth.entity.repository.dto;

import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.tools.UserDTOBuilder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
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

    private String activated;

    @Past
    private Date birthday;

    private int age;

    private Boolean enable;

    private Timestamp createdDate;

    private List<RoleDTO> roles;

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
        this.activated = user.getActivated();
        this.birthday = user.getBirthday();
        this.age = user.getAge();
        this.enable = user.isEnable();
        this.createdDate = user.getCreatedDate();
        this.roles = user.getRoles();
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
        this.activated = user.getActivated();
        this.birthday = user.getBirthday();
        this.age = user.getAge();
        this.enable = user.getEnable();
        this.createdDate = user.getCreatedDate();
//        List<RoleDTO> listRoles = new ArrayList<>();
//        for (Roles rol : user.getRoles()) {
//            RoleDTO r = new RoleDTO(rol);
//            listRoles.add(r);
//        }
//        this.roles = listRoles;
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

    public String getActivated() {
        return activated;
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

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setActivated(String activated) {
        this.activated = activated;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
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
        if (id != null) {
            joiner.add(id.toString());
        }
        joiner.add(login)
                .add(name)
                .add(surname)
                .add(patronymic)
                .add(mail)
                .add(gender)
                .add(phone)
                .add(Integer.toString(age));
        return joiner.toString();
    }

    public boolean isEmpty() {
        return !this.login.isEmpty() || !this.name.isEmpty() || !this.surname.isEmpty() || !this.mail.isEmpty();
    }
}