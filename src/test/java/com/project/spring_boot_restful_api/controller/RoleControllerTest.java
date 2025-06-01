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

import java.util.HashSet;
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
                Role role = Role.builder().name("test2").build();

                mockMvc.perform(put(ROLE_PATH + "/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindByName() throws Exception {
                mockMvc.perform(get(ROLE_PATH + "/test"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindAll() throws Exception {
                mockMvc.perform(get(ROLE_PATH))
                                .andExpect(status().isOk());
        }

        @Test
        void testSaveFindByNameUpdateAndDeleteById() throws Exception {
                Role role = Role.builder().name("test").build();

                // save without authorities set
                mockMvc.perform(post(ROLE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isBadRequest());
                // save
                Authority authorityTest = authorityService.save(Authority.builder().name("test").build());
                role.setAuthorities(Set.of(authorityTest));
                mockMvc.perform(post(ROLE_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isOk());
                // findByName
                mockMvc.perform(get(ROLE_PATH + "/test"))
                                .andExpect(status().isOk());
                // update without authorities set
                role.setAuthorities(new HashSet<>());
                mockMvc.perform(put(ROLE_PATH + "/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isBadRequest());
                // update
                Authority authorityTest2 = authorityService.save(Authority.builder().name("test2").build());
                Set<Authority> authorities = new HashSet<>();
                authorities.add(authorityTest);
                authorities.add(authorityTest2);
                role.setName("test2");
                role.setAuthorities(authorities);
                mockMvc.perform(put(ROLE_PATH + "/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(role)))
                                .andExpect(status().isOk());
                // deleteById
                mockMvc.perform(delete(ROLE_PATH).param("id", "1"))
                                .andExpect(status().isOk());
        }
}
