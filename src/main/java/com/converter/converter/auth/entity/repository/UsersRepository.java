package com.converter.converter.auth.entity.repository;

import com.converter.converter.auth.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT * FROM users " +
            " LEFT JOIN users_roles ON users.id = users_roles.users_id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.id=?;", nativeQuery = true)
    Optional<Users> findUserById(@Param("id") Long id);

    @Query(value = "SELECT * FROM users " +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.login=?;", nativeQuery = true)
    Optional<Users> findUserByLogin(String login);

    @Query(value = "SELECT * FROM users " +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.name = ? AND users.surname = ? AND users.patronymic = ?;", nativeQuery = true)
    List<Users> findUserByNameAndSurnameAndPatronymic(String name, String surname, String patronymic);

    @Query(value = "SELECT * FROM users " +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.mail=?;", nativeQuery = true)
    Optional<Users> findUserByMail(String mail);

    @Query(value = "SELECT * FROM users" +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.gender=?;", nativeQuery = true)
    List<Users> findUserByGender(String gender);

    @Query(value = "SELECT * FROM users" +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.phone=?;", nativeQuery = true)
    Optional<Users> findUserByPhone(String phone);

    @Query(value = "SELECT * FROM users " +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.birthday=?;", nativeQuery = true)
    List<Users> findUserByBirthday(Date birthday);

    @Query(value = "SELECT * FROM users" +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.age=?;", nativeQuery = true)
    List<Users> findUserByAge(int age);

    @Query(value = "SELECT * FROM users" +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.enable=?;", nativeQuery = true)
    List<Users> findUserByEnable(boolean enable);

    @Query(value = "SELECT * FROM users" +
            " LEFT JOIN users_roles ON users_roles.users_id = users.id " +
            " LEFT JOIN roles ON roles.id = users_roles.roles_id " +
            " WHERE users.created=?;", nativeQuery = true)
    List<Users> findUserByCreated(Timestamp created);

    @Modifying
    @Query(value = "DELETE FROM users WHERE users.id=:id AND users.login=:login", nativeQuery = true)
    void deleteUserByIdAndLogin(@Param("id") Long id, @Param("login") String login);

    @Query(value = "SELECT TOP 1 * FROM users ORDER BY users.id DESC;", nativeQuery = true)
    Users lastUser();

    @Modifying
    @Query(value = "UPDATE dbo.users SET users.login = :login, users.name = :name, users.surname = :surname, users.patronymic = :patronymic," +
            " users.password = :password, users.gender = :gender, users.phone = :phone, users.mail = :mail, users.birthday = :birthday," +
            " users.age = :age, users.enable = :enable, users.created = :createdDate WHERE users.id = :id ;", nativeQuery = true)
    void updateUserData(@Param("login") String login,
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
                        @Param("createdDate") Timestamp createdDate,
                        @Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO users (users.login, users.name, users.surname, users.patronymic, users.password, " +
            "users.gender, users.phone, users.mail, users.birthday, users.age, users.enable, users.created)" +
            " VALUES (:login, :name, :surname, :patronymic, :password, :gender, :phone, :mail, :birthday, :age, :enable," +
            " :createdDate);", nativeQuery = true)
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

    @Modifying
    @Query(value = "INSERT INTO users_roles (users_roles.users_id, users_roles.roles_id) VALUES(:user_id, :role_id);", nativeQuery = true)
    void saveRoleForUser(@Param("user_id") Long user_id, @Param("role_id") Long role_id);
}