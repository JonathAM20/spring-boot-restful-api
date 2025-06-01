package com.project.spring_boot_restful_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.spring_boot_restful_api.exception.ModelEntityNotFoundException;
import com.project.spring_boot_restful_api.exception.NullModelEntityPropertyValueException;
import com.project.spring_boot_restful_api.model.Authority;
import com.project.spring_boot_restful_api.repository.AuthorityRepository;

@Service
public class AuthorityService {

    private AuthorityRepository authorityRepository;

    public AuthorityService(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    public Authority save(final Authority authority) {
        var newAuthority = Authority.builder()
                .name(authority.getName())
                .build();

        if (newAuthority.getName() == null) {
            throw new NullModelEntityPropertyValueException("Invalid Authority, null property: name");
        } else if (newAuthority.getName().isEmpty()) {
            throw new NullModelEntityPropertyValueException("Invalid Authority, empty property: name");
        }

        return authorityRepository.save(newAuthority);
    }

    public Authority update(final String name, final Authority authority) {
        var authorityToUpdate = findByName(name);
        authorityToUpdate.setName(authority.getName());

        if (authorityToUpdate.getName() == null) {
            throw new NullModelEntityPropertyValueException("Invalid Authority, null property: name");
        } else if (authorityToUpdate.getName().isEmpty()) {
            throw new NullModelEntityPropertyValueException("Invalid Authority, empty property: name");
        }

        return authorityRepository.save(authorityToUpdate);
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
