package com.project.spring_boot_restful_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.spring_boot_restful_api.model.Authority;
import com.project.spring_boot_restful_api.model.Role;
import com.project.spring_boot_restful_api.model.User;
import com.project.spring_boot_restful_api.service.AuthorityService;
import com.project.spring_boot_restful_api.service.RoleService;
import com.project.spring_boot_restful_api.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private AuthorityService authorityService;

        @Autowired
        private RoleService roleService;

        @Autowired
        private UserService userService;

        private final String USER_PATH = "/user";

        @Test
        void testDeleteById() throws Exception {
                mockMvc.perform(delete(USER_PATH).param("id", "1"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testUpdate() throws Exception {
                User user = User.builder().username("userT2").build();

                mockMvc.perform(put(USER_PATH + "/userT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindByUsername() throws Exception {
                mockMvc.perform(get(USER_PATH + "/userT"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindAll() throws Exception {
                mockMvc.perform(get(USER_PATH))
                                .andExpect(status().isOk());
        }

        @Test
        void testSaveWithInvalidPropertyValues() throws Exception {
                User userWithNullPropertyValues = User.builder().build();

                // save with null property values
                mockMvc.perform(post(USER_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userWithNullPropertyValues)))
                                .andExpect(status().isBadRequest());

                Authority authority = authorityService.save(Authority.builder().name("authorityT").build());
                Role role = roleService.save(Role.builder().name("roleT").authorities(Set.of(authority)).build());

                // save with null username
                User userWithNullUsername = User.builder().password("123456").roles(Set.of(role)).build();
                mockMvc.perform(post(USER_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userWithNullUsername)))
                                .andExpect(status().isBadRequest());

                // save with null password
                User userWithNullPassword = User.builder().username("userT").roles(Set.of(role)).build();
                mockMvc.perform(post(USER_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userWithNullPassword)))
                                .andExpect(status().isBadRequest());

                // save with null roles
                User userWithNullRoles = User.builder().username("userT").password("123456").build();
                mockMvc.perform(post(USER_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userWithNullRoles)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void TestSaveAndUpdateWithInvalidPropertyValues() throws Exception {
                Authority authority = authorityService.save(Authority.builder().name("authorityT").build());
                Role role = roleService.save(Role.builder().name("roleT").authorities(Set.of(authority)).build());
                User user = User.builder().username("userT").password("123456").roles(Set.of(role)).build();

                // update with null username
                user.setUsername(null);
                mockMvc.perform(put(USER_PATH + "/userT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isBadRequest());

                // update with null password
                user.setUsername("userT");
                user.setPassword(null);
                mockMvc.perform(put(USER_PATH + "/userT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isBadRequest());

                // update with null roles
                user.setUsername("userT");
                user.setPassword("123456");
                user.setRoles(null);
                mockMvc.perform(put(USER_PATH + "/userT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testSaveFindByNameUpdateAndDeleteById() throws Exception {

                Authority authority = authorityService.save(Authority.builder().name("authorityT").build());
                Role adminRole = roleService.save(Role.builder().name("roleT").authorities(Set.of(authority)).build());
                Role userRole = roleService.save(Role.builder().name("roleT2").authorities(Set.of(authority)).build());

                User user = User.builder().username("userT").password("123456").roles(Set.of(adminRole)).build();

                // save
                mockMvc.perform(post(USER_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isOk());

                // findByName
                mockMvc.perform(get(USER_PATH + "/userT"))
                                .andExpect(status().isOk());

                // update
                user.setUsername("userT2");
                user.setPassword("654321");
                user.setRoles(Set.of(userRole));
                mockMvc.perform(put(USER_PATH + "/userT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(user)))
                                .andExpect(status().isOk());

                                assertEquals(1, userService.findAll().size());


                // deleteById
                mockMvc.perform(delete(USER_PATH).param("id", "1"))
                                .andExpect(status().isOk());
        }
}
