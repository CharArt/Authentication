package com.converter.converter.auth;

import com.converter.converter.auth.entity.GoogleUser;
import com.converter.converter.auth.entity.Roles;
import com.converter.converter.auth.service.GoogleUsersService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class GoogleUserServiceTest {

    private final GoogleUsersService service;

    @Autowired
    public GoogleUserServiceTest(GoogleUsersService service) {
        this.service = service;
    }

    @Test
    void findUserByIdTest() {
        assertEquals(service.findUserById(1L).getId(), 1L);
    }

    @Test
    void findUserBySubTest() {
        assertEquals(service.findUserBySub("112663179165874471763").getSub(), "112663179165874471763");
    }

    @Test
    void findUserByNameAndSurnameAndEmailTest() {
        GoogleUser googleUser = service.findUserByNameAndSurnameAndEmail("Artoym", "Charykov", "charartpav@gmail.com");
        for (Roles role : googleUser.getRolesList()) {
            System.out.println(role.getRole());
        }
        assertEquals(googleUser.getSub(), "112663179165874471763");
    }

    @Test
    void deleteGoogleUserByIdTest() {
        service.deleteGoogleUserById(1L);
        assertThrows(EntityNotFoundException.class, () -> {
            service.findUserById(1L);
        });
    }
}