package com.project.spring_boot_restful_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.spring_boot_restful_api.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(final String name);

}
