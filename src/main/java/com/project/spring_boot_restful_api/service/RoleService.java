package com.project.spring_boot_restful_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

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
                .build();
        roleRepository.save(newRole);
    }

    public void update(final Role role) {
        roleRepository.save(role);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Optional<Role> findByUsername(final String name) {
        return roleRepository.findByName(name);
    }

    public void deleteById(final Long id) {
        roleRepository.deleteById(id);
    }

}
