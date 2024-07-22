package com.example.innova;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findByUsername(String username);
    @Modifying
    @Transactional
    @Query("UPDATE UserModel u SET u.username = :username, u.email = :email,u.password = :password, u.role = :role WHERE u.id = :id")
    void updateUser(@Param("id") Long id, @Param("username") String username, @Param("email") String email,@Param("password") String password, @Param("role") String role);
}
