package com.project.spring_boot_restful_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.spring_boot_restful_api.exception.ModelEntityNotFoundException;
import com.project.spring_boot_restful_api.exception.NullModelEntityPropertyValueException;
import com.project.spring_boot_restful_api.model.User;
import com.project.spring_boot_restful_api.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(final User user) {
        var newUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .build();

        if (newUser.getUsername() == null || newUser.getUsername().isBlank()) {
            throw new NullModelEntityPropertyValueException("Invalid User, null property: username");
        } else if (newUser.getPassword() == null || newUser.getPassword().isBlank()) {
            throw new NullModelEntityPropertyValueException("Invalid User, null property: password");
        } else if (newUser.getRoles() == null || newUser.getRoles().isEmpty()) {
            throw new NullModelEntityPropertyValueException("Invalid User, null property: role");
        }

        return userRepository.save(newUser);
    }

    public User update(final String username, final User user) {
        var userToUpdate = findByUsername(username);
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setPassword(user.getPassword());
        userToUpdate.setRoles(user.getRoles());

        if (userToUpdate.getUsername() == null || userToUpdate.getUsername().isBlank()) {
            throw new NullModelEntityPropertyValueException("Invalid User, null property: username");
        } else if (userToUpdate.getPassword() == null || userToUpdate.getPassword().isBlank()) {
            throw new NullModelEntityPropertyValueException("Invalid User, null property: password");
        } else if (userToUpdate.getRoles() == null || userToUpdate.getRoles().isEmpty()) {
            throw new NullModelEntityPropertyValueException("Invalid User, null property: role");
        }

        return userRepository.saveAndFlush(userToUpdate);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByUsername(final String username) {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new ModelEntityNotFoundException(
                        String.format("User not found, invalid name: [%s]", username)));
    }

    public User findById(final Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new ModelEntityNotFoundException(
                        String.format("User not found, invalid id: [%d]", id)));
    }

    public void deleteById(final Long id) {
        var userToDelete = findById(id);
        userRepository.deleteById(userToDelete.getId());
    }

}
