package com.project.spring_boot_restful_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.spring_boot_restful_api.model.Authority;
import com.project.spring_boot_restful_api.repository.AuthorityRepository;

@Service
public class AuthorityService {

    private AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public void save(final Authority authority) {
        var newAuthority = Authority.builder()
                .name(authority.getName())
                .build();
        authorityRepository.save(newAuthority);
    }

    public void update(final Authority authority) {
        authorityRepository.save(authority);
    }

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    public Optional<Authority> findByName(final String name) {
        return authorityRepository.findByName(name);
    }

    public void deleteById(final Long id) {
        authorityRepository.deleteById(id);
    }

}
