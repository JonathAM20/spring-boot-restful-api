package com.project.spring_boot_restful_api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.spring_boot_restful_api.model.User;
import com.project.spring_boot_restful_api.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void save(final User user) {
        var newUser = User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
        userRepository.save(newUser);
    }

    public void update(final User user) {
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(final String username) {
        return userRepository.findByUsername(username);
    }

    public void deleteById(final Long id) {
        userRepository.deleteById(id);
    }

}
