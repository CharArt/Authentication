package com.converter.converter.auth.repository;

import com.converter.converter.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT * FROM users AS u WHERE u.login=?;", nativeQuery = true)
    Optional<Users> findUserByLogin(String login);

    @Query(value = "SELECT * FROM users AS u WHERE u.name = ? AND u.surname = ? AND u.patronymic = ?;", nativeQuery = true)
    List<Users> findUserByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);

    @Query(value = "SELECT * FROM users AS u WHERE u.mail=?;", nativeQuery = true)
    Optional<Users> findUserByMail(String mail);

    @Query(value = "SELECT * FROM users AS u WHERE u.gender=?;", nativeQuery = true)
    List<Users> findUserByGender(String gender);

    @Query(value = "SELECT * FROM users AS u WHERE u.phone=?;", nativeQuery = true)
    Optional<Users> findUserByPhone(String phone);

    @Query(value = "SELECT * FROM users AS u WHERE u.birthday=?;", nativeQuery = true)
    List<Users> findUserByBirthday(Date birthday);

    @Query(value = "SELECT * FROM users AS u WHERE u.age=?;", nativeQuery = true)
    List<Users> findUserByAge(int age);

    @Query(value = "SELECT * FROM users AS u WHERE u.enable=?;", nativeQuery = true)
    List<Users> findUserByEnable(boolean enable);

    @Query(value = "SELECT * FROM users AS u WHERE u.created=?;", nativeQuery = true)
    List<Users> findUserByCreated(Timestamp created);

    @Modifying
    @Query(value = "DELETE FROM users WHERE users.id=:id AND users.login=:login", nativeQuery = true)
    void deleteUserByIdAndLogin(@Param("id") Long id, @Param("login") String login);

    @Modifying
    @Query(value = "UPDATE users SET users.login = :login, users.name = :name, users.surname = :surname, users.patronymic = :patronymic, " +
            "users.password = :password, users.gender = :gender, users.phone = :phone, users.mail = :mail, users.birthday = :birthday, " +
            "users.age = :age, users.enable = :enable, users.created = :createdDate WHERE users.id = :id", nativeQuery = true)
    void updateUserData(@Param("id") Long id,
                        @Param("login") String login,
                        @Param("name") String name,
                        @Param("surname") String surname,
                        @Param("patronymic") String patronymic,
                        @Param("password") String password,
                        @Param("gender") String gender,
                        @Param("phone") String phone,
                        @Param("mail") String mail,
                        @Param("birthday") Date birthday,
                        @Param("age") int age,
                        @Param("enable") boolean enable,
                        @Param("createdDate") Timestamp createdDate);

    @Modifying
    @Query(value = "INSERT INTO users (users.login, users.name, users.surname, users.patronymic, users.password, users.gender, users.phone, users.mail, users.birthday," +
            "users.age, users.enable, users.created) values (:login, :name, :surname, :patronymic, :password, :gender, :phone, " +
            ":mail, :birthday, :age, :enable, :createdDate)", nativeQuery = true)
    void saveUser(@Param("login") String login,
                  @Param("name") String name,
                  @Param("surname") String surname,
                  @Param("patronymic") String patronymic,
                  @Param("password") String password,
                  @Param("gender") String gender,
                  @Param("phone") String phone,
                  @Param("mail") String mail,
                  @Param("birthday") Date birthday,
                  @Param("age") int age,
                  @Param("enable") boolean enable,
                  @Param("createdDate") Timestamp createdDate);
}