package com.converter.converter.auth.service;

import com.converter.converter.auth.entity.Roles;
import com.converter.converter.auth.entity.Users;
import com.converter.converter.auth.repository.RolesRepository;
import com.converter.converter.auth.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UsersRepository repository;
    private final RolesRepository rolesRepository;

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    public UserServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.repository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public Users findUserById(Long userId) {
        logger.info("Start_Method_findUserById(" + userId + ")");
        return repository.findUserById(userId).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Users findUserByLogin(String login) {
        logger.info("Start_Method_findUserByLogin(" + login + ")");
        return repository.findUserByLogin(login).orElse(new Users());
    }

    @Override
    public List<Users> findUserByNameAndSurnameAndPatronymic(String name, String surname, String patronymic) {
        logger.info("Start_Method_findUserByNameAndSurnameAndPatronymic(" + name + "," + surname + "," + patronymic + ")");
        List<Users> usersList = repository.findUserByNameAndSurnameAndPatronymic(name, surname, patronymic).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters_(" + name + "," + surname + "," + patronymic + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByGender(String gender) {
        logger.info("Start_Method_findUserByGender(" + gender + ")");
        List<Users> usersList = repository.findUserByGender(gender);
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters(" + gender + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByBirthday(Date birthday) {
        logger.info("Start_Method_findUserByBirthday(" + birthday + ")");
        List<Users> usersList = repository.findUserByBirthday(birthday).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters(" + birthday + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByAge(int age) {
        logger.info("Start_Method_findUserByAge(" + age + ")");
        List<Users> usersList = repository.findUserByAge(age).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters(" + age + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByEnable(boolean enable) {
        logger.info("Start_Method_findUserByEnable(" + enable + ")");
        List<Users> usersList = repository.findUserByEnable(enable).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters(" + enable + ")_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findUserByCreated(Timestamp created) {
        logger.info("Start_Method_findUserByCreated(" + created + ")");
        List<Users> usersList = repository.findUserByCreated(created).stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters_not_exist!");
            throw new EntityNotFoundException();
        }
        return usersList;
    }

    @Override
    public List<Users> findAll() {
        logger.info("Start_Method_findAll");
        List<Users> usersList = repository.findAll().stream().toList();
        if (usersList.isEmpty()) {
            logger.error("Exception_user_with_this_parameters_not_exist!");
            throw new EntityNotFoundException();
        }
        return repository.findAll();
    }

    @Override
    public Users findUserByMail(String mail) {
        logger.info("Start_Method_findUserByMail(" + mail + ")");
        return repository.findUserByMail(mail).orElse(new Users());
    }

    @Override
    public Users findUserByPhone(String phone) {
        logger.info("Start_Method_findUserByPhone(" + phone + ")");
        return repository.findUserByPhone(phone).orElse(new Users());
    }

    @Override
    public Users getLastPerson() {
        return repository.lastUser();
    }

    @Override
    public void create(Users user) {
        logger.info("Start_Method_create");
        if (user.isEmpty()) {
            logger.error("Exception_user_does_not_be_empty!");
            throw new EntityExistsException();
        }
        repository.save(user);
    }

    @Override
    public void deleteUserByIdAndLogin(Long id, String login) {
        logger.info("Start_Method_deleteUserByIdAndLogin(" + id + ", " + login + ")");
        if (repository.findUserById(id).isPresent()) {
            if (repository.findUserByLogin(login).isPresent()) {
                repository.deleteUserByIdAndLogin(id, login);
            }
        }
    }

    @Override
    public void updateUser(Users user) {
        logger.info("Start_Method_deleteUserByIdAndLogin(user)");
        repository.updateUserData(user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                user.getMail(),
                user.getBirthday(),
                user.getAge(),
                user.getEnable(),
                user.getCreatedDate(),
                user.getId());
    }

    @Override
    public void saveNewUser(Users user) {
        logger.info("Start_Method_deleteUserByIdAndLogin(user)");
        repository.saveUser(user.getLogin(),
                user.getName(),
                user.getSurname(),
                user.getPatronymic(),
                user.getPassword(),
                user.getGender(),
                user.getPhone(),
                user.getMail(),
                user.getBirthday(),
                user.getAge(),
                user.getEnable(),
                user.getCreatedDate());
    }

    @Override
    public void saveRoleForUser(Users user) {
        List<Users> list = repository.findUserByNameAndSurnameAndPatronymic(user.getName(), user.getSurname(), user.getPatronymic());
        Users targetUser = new Users();
        for (Users users : list) {
            if (users.getMail().equals(user.getMail())) {
                targetUser = users;
            }
        }
        if (user.getRoles().size() == 0) {
            Roles defaultRole = rolesRepository.findByRoleName("USER");
            repository.saveRoleForUser(targetUser.getId(), defaultRole.getId());
        } else {
            for (Roles role : user.getRoles()) {
                repository.saveRoleForUser(targetUser.getId(), role.getId());
            }
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        logger.info("Start_Method_loadUserByUsername(" + username + ")");
        return repository.findUserByLogin(username).orElseThrow(EntityNotFoundException::new);
    }
}