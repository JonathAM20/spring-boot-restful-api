package com.project.spring_boot_restful_api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.spring_boot_restful_api.model.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Optional<Authority> findByName(final String name);

}
