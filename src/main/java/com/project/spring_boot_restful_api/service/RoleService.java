package com.project.spring_boot_restful_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.spring_boot_restful_api.exception.ModelEntityNotFoundException;
import com.project.spring_boot_restful_api.exception.NullModelEntityPropertyValueException;
import com.project.spring_boot_restful_api.model.Role;
import com.project.spring_boot_restful_api.repository.RoleRepository;

@Service
public class RoleService {

    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public void save(final Role role) {
        var newRole = Role.builder()
                .name(role.getName())
                .authorities(role.getAuthorities())
                .build();

        if (role.getAuthorities() == null) {
            throw new NullModelEntityPropertyValueException("Invalid Role, null property: authorities");
        } else if (role.getAuthorities().isEmpty()) {
            throw new NullModelEntityPropertyValueException("Invalid Role, empty property: authorities");
        }
        roleRepository.save(newRole);
    }

    public void update(final String name, final Role role) {
        var roleToUpdate = findByName(name);
        roleToUpdate.setName(role.getName());
        roleToUpdate.setAuthorities(role.getAuthorities());

        if (role.getAuthorities() == null) {
            throw new NullModelEntityPropertyValueException("Invalid Role, null property: authorities");
        } else if (role.getAuthorities().isEmpty()) {
            throw new NullModelEntityPropertyValueException("Invalid Role, empty property: authorities");
        }
        roleRepository.save(roleToUpdate);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role findByName(final String name) {
        return roleRepository
                .findByName(name)
                .orElseThrow(() -> new ModelEntityNotFoundException(
                        String.format("Role not found, invalid name: %s", name)));
    }

    public Role findById(final Long id) {
        return roleRepository
                .findById(id)
                .orElseThrow(() -> new ModelEntityNotFoundException(
                        String.format("Role not found, invalid id: %d", id)));
    }

    public void deleteById(final Long id) {
        var roleToDelete = findById(id);
        roleRepository.deleteById(roleToDelete.getId());
    }

}
