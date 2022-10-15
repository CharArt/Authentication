package com.converter.converter.auth.entity.repository;

import com.converter.converter.auth.entity.GoogleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoogleUserRepository extends JpaRepository<GoogleUser, Long> {

    @Query(value = "SELECT * FROM google_users" +
            " LEFT JOIN users ON users.id = google_users.user_id" +
            " LEFT JOIN google_users_roles ON google_users_roles.google_id = google_users.user_id" +
            " LEFT JOIN roles ON google_users_roles.roles_id = roles.id" +
            " WHERE google_users.id = :id ;", nativeQuery = true)
    Optional<GoogleUser> findGoogleUserById(@Param("id") Long id);

    @Query(value = "SELECT * FROM google_users" +
            " LEFT JOIN users ON users.id = google_users.user_id" +
            " LEFT JOIN google_users_roles ON google_users_roles.google_id = google_users.user_id" +
            " LEFT JOIN roles ON google_users_roles.roles_id = roles.id" +
            " WHERE google_users.sub = :sub ;", nativeQuery = true)
    Optional<GoogleUser> findGoogleUserBySub(@Param("sub") String sub);

    @Query(value = "SELECT * FROM google_users" +
            " LEFT JOIN users ON users.id = google_users.user_id" +
            " LEFT JOIN google_users_roles ON google_users_roles.google_id = google_users.user_id" +
            " LEFT JOIN roles ON google_users_roles.roles_id = roles.id" +
            " WHERE google_users.given_name = :name" +
            " AND google_users.family_name = :surname" +
            " AND google_users.email = :email ;", nativeQuery = true)
    Optional<GoogleUser> findGoogleUserByNameAndSurnameAndEmail(@Param("name") String name, @Param("surname") String surname, @Param("email") String email);

    @Modifying
    @Query(value = "DELETE FROM google_users WHERE google_users.user_id=:id ;", nativeQuery = true)
    void deleteGoogleUserById(@Param("id") Long id);

    @Modifying
    @Query(value = "INSERT INTO google_users (user_id, sub, given_name, family_name, email, email_verified, locale) " +
            "VALUES (:user_id, :sub, :given_name, :family_name, :email, :email_verified, :locale) ;", nativeQuery = true)
    void saveNewGoogleUsers(@Param("user_id") Long user_id,
                            @Param("sub") String sub,
                            @Param("given_name") String given_name,
                            @Param("family_name") String family_name,
                            @Param("email") String email,
                            @Param("email_verified") Boolean email_verified,
                            @Param("locale") String locale);

    @Modifying
    @Query(value = "INSERT INTO google_users_roles (google_users_roles.google_id, google_users_roles.roles_id) VALUES(:google_id, :role_id) ;", nativeQuery = true)
    void saveRoleForGoogleUser(@Param("google_id") Long user_id, @Param("role_id") Long role_id);
}