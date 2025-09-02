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
import com.project.spring_boot_restful_api.service.AuthorityService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private AuthorityService authorityService;

        private final String ROLE_PATH = "/role";

        @Test
        void testDeleteById() throws Exception {
                mockMvc.perform(delete(ROLE_PATH).param("id", "1"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testUpdate() throws Exception {
                Role role = Role.builder().name("roleT2").build();

                mockMvc.perform(put(ROLE_PATH + "/roleT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindByName() throws Exception {
                mockMvc.perform(get(ROLE_PATH + "/roleT"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindAll() throws Exception {
                mockMvc.perform(get(ROLE_PATH))
                                .andExpect(status().isOk());
        }

        @Test
        void testSaveWithInvalidProtertyValues() throws Exception {

                // save with null property values
                Role roleWithNullPropertyValue = Role.builder().name(null).authorities(null).build();
                mockMvc.perform(post(ROLE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(roleWithNullPropertyValue)))
                                .andExpect(status().isBadRequest());

                // save with null name
                Authority authority = authorityService.save(Authority.builder().name("authorityT").build());
                Role roleWithNullName = Role.builder().name(null).authorities(Set.of(authority)).build();
                mockMvc.perform(post(ROLE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(roleWithNullName)))
                                .andExpect(status().isBadRequest());

                // save with null authorities
                Role roleWithNullAuthorities = Role.builder().name("roleT").authorities(null).build();
                mockMvc.perform(post(ROLE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(roleWithNullAuthorities)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testSaveAndUpdateWithInvalidPropertyValues() throws Exception {

                Authority authority = authorityService.save(Authority.builder().name("authorityT").build());
                Role role = Role.builder().name("roleT").authorities(Set.of(authority)).build();

                // save
                mockMvc.perform(post(ROLE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isOk());

                // update with null name
                Role roleWithNullName = Role.builder().name(null).authorities(Set.of(authority)).build();
                mockMvc.perform(put(ROLE_PATH + "/roleT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(roleWithNullName)))
                                .andExpect(status().isBadRequest());

                // save with null authorities
                Role roleWithNullAuthorities = Role.builder().name("roleT").authorities(null).build();
                mockMvc.perform(put(ROLE_PATH + "/roleT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(roleWithNullAuthorities)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testSaveFindByNameUpdateAndDeleteById() throws Exception {

                Authority readAuthority = authorityService.save(Authority.builder().name("authorityT").build());
                Authority writeAuthority = authorityService.save(Authority.builder().name("authorityT2").build());
                Role role = Role.builder().name("roleT").authorities(Set.of(readAuthority)).build();

                // save
                mockMvc.perform(post(ROLE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isOk());
                // findByName
                mockMvc.perform(get(ROLE_PATH + "/roleT"))
                                .andExpect(status().isOk());
                // update
                role.setName("roleT2");
                role.setAuthorities(Set.of(readAuthority, writeAuthority));
                mockMvc.perform(put(ROLE_PATH + "/roleT")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isOk());
                // deleteById
                mockMvc.perform(delete(ROLE_PATH).param("id", "1"))
                                .andExpect(status().isOk());
        }
}
