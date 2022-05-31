package com.converter.converter.auth;

import com.converter.converter.auth.entity.Roles;
import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.repository.dto.UserDTO;
import com.converter.converter.auth.service.UserService;
import com.converter.converter.auth.tools.UserDTOBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class UserServiceTest {

    private UserService service;

    @Autowired
    public UserServiceTest(UserService service) {
        this.service = service;
    }

    class OneUser {
        public static Users getOneUser() {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");
            String dateTime1 = "2022-03-28 21:53:08.1866666";
            String dateTime2 = "2022-03-28 14:04:48.4533333";
            LocalDate ld1 = LocalDate.parse(dateTime1, format);
            LocalDateTime ldt1 = LocalDateTime.parse(dateTime1, format);
            LocalDate ld2 = LocalDate.parse(dateTime2, format);
            LocalDateTime ldt2 = LocalDateTime.parse(dateTime2, format);

            Date birthday = Date.valueOf("1993-02-26");

            List<Roles> rolesList = new ArrayList<>();
            Roles roles = new Roles();
            roles.setRole("ADMIN");
            roles.setId(1L);
            rolesList.add(roles);

            Users user = new Users();
            user.setId(1L);
            user.setLogin("CharArtPav");
            user.setName("Artem");
            user.setSurname("Charykov");
            user.setPatronymic("Pavlovich");
            user.setMail("ArtPavChar@gmail.com");
            user.setPassword("$2a$12$ycUYSXfP1a5Q6tF71oTSDO4i7Mi/LKM8N6fiETqlA3KFqFYs36NXK");
            user.setGender("male");
            user.setPhone("89015891274");
            user.setBirthday(birthday);
            user.setAge(ld2.getYear() - user.getBirthday().getYear());
            user.setEnable(true);
            user.setCreatedDate(Timestamp.valueOf(ldt2));
            user.setRoles(rolesList);
            return user;
        }
    }

    @Test
    void findUserByIdTest() {
        UserDTO userDTO = new UserDTO(OneUser.getOneUser());
        assertEquals(service.findUserById(1L).getId(), userDTO.getId());
    }

    @Test
    void findUserByLoginTest() {
        assertEquals(service.findUserByLogin("CharArtPav").getLogin(), "CharArtPav");
    }

    @Test
    void findUserByNameAndSurnameAndPatronymicTest() {
        assertEquals(service.findUserByNameAndSurnameAndPatronymic("Artem", "Charykov", "Pavlovich").stream().findFirst().get().getId(), 1L);
    }

    @Test
    void findUserByMailTest() {
        assertEquals(service.findUserByMail("ArtPavChar@gmail.com").getLogin(), "CharArtPav");
    }

    @Test
    void findUserByGenderTest() {
        assertEquals(service.findUserByGender("male").stream().findFirst().get().getGender(), "male");
    }

    @Test
    void findUserByBirthdayTest() {
        assertEquals(service.findUserByBirthday(OneUser.getOneUser().getBirthday()).stream().findFirst().get().getBirthday(), OneUser.getOneUser().getBirthday());
    }

    @Test
    void findUserByAgeTest() {
        assertEquals(service.findUserByAge(29).stream().findFirst().get().getAge(), 29);
    }

    @Test
    void findUserByEnableTest() {
        assertTrue(service.findUserByEnable(true).stream().findFirst().get().getEnable());
    }

    @Test
    void findUserByCreatedTest() {
        assertEquals(service.findUserByCreated(OneUser.getOneUser().getCreatedDate()).stream().findFirst().get().getCreatedDate(), OneUser.getOneUser().getCreatedDate());
    }

    @Test
    void findAllTest() {
        assertEquals(service.findAll().size(), 11);
    }

    @Test
    void createTest() {
        List<Roles> rolesList = new ArrayList<>();
        Roles roles = new Roles();
        roles.setRole("ADMIN");
        roles.setId(1L);
        rolesList.add(roles);

        UserDTOBuilder builder = new UserDTOBuilder();
        builder.setLogin("Test2");
        builder.setName("Test2");
        builder.setSurname("Test2");
        builder.setPatronymic("Test2");
        builder.setPassword("Test2");
        builder.setMail("Test2");
        builder.setPhone("89018591235");
        builder.setBirthday(Date.valueOf("1993-02-16"));
        UserDTO userDTO = builder.build();
        Users user = new Users(userDTO);
        user.setRoles(rolesList);
        service.create(user);
        assertEquals(service.findAll().size(), 12);
    }

    @Test
    void deleteUserByIdAndLoginTest() {
        service.deleteUserByIdAndLogin(1L, "CharArtPav");
        assertEquals(service.findAll().size(), 10);
    }


    @Test
    void updateUserTest() {
        List<Roles> rolesList = new ArrayList<>();
        Roles roles = new Roles();
        roles.setRole("USER");
        roles.setId(1L);
        rolesList.add(roles);

        Users users = service.findUserById(1L);
        users.setRoles(rolesList);

        service.updateUser(users);

        assertEquals(service.findUserById(1L).getRoles().stream().findFirst().get().getRole().toLowerCase(), "user");
    }

    @Test
    void saveNewUserTest () {
        List<Roles> rolesList = new ArrayList<>();
        Roles roles = new Roles();
        roles.setRole("ADMIN");
        roles.setId(1L);
        rolesList.add(roles);

        UserDTOBuilder builder = new UserDTOBuilder();
        builder.setLogin("Test2");
        builder.setName("Test2");
        builder.setSurname("Test2");
        builder.setPatronymic("Test2");
        builder.setPassword("Test2");
        builder.setMail("Test2");
        builder.setPhone("89018591235");
        builder.setBirthday(Date.valueOf("1993-02-16"));
        UserDTO userDTO = builder.build();
        Users user = new Users(userDTO);
        user.setRoles(rolesList);
        service.saveNewUser(user);
        assertEquals(service.findAll().size(), 11);
    }
}
