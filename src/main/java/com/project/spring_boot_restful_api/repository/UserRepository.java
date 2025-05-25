package com.project.spring_boot_restful_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.spring_boot_restful_api.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(final String username);

}
