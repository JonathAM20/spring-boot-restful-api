package com.project.spring_boot_restful_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.spring_boot_restful_api.exception.ModelEntityNotFoundException;
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

    public void update(final String name, final Authority authority) {
        var authorityToUpdate = findByName(name);
        authorityToUpdate.setName(authority.getName());
        authorityRepository.save(authorityToUpdate);
    }

    public List<Authority> findAll() {
        return authorityRepository.findAll();
    }

    public Authority findByName(final String name) {
        return authorityRepository
                .findByName(name)
                .orElseThrow(() -> new ModelEntityNotFoundException(
                        String.format("Authority not found, invalid name: [%s]", name)));
    }

    public Authority findById(final Long id) {
        return authorityRepository
                .findById(id)
                .orElseThrow(() -> new ModelEntityNotFoundException(
                        String.format("Authority not found, invalid id: [%d]", id)));
    }

    public void deleteById(final Long id) {
        var authorityToDelete = findById(id);
        authorityRepository.deleteById(authorityToDelete.getId());
    }

}
