package com.project.spring_boot_restful_api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.project.spring_boot_restful_api.model.Authority;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorityControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        private final String AUTHORITY_PATH = "/authority";

        @Test
        void testDeleteById() throws Exception {
                mockMvc.perform(delete(AUTHORITY_PATH).param("id", "1"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testUpdate() throws Exception {
                Authority authority = Authority.builder().name("test2").build();

                mockMvc.perform(put(AUTHORITY_PATH + "/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindByName() throws Exception {
                mockMvc.perform(get(AUTHORITY_PATH + "/test"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindAll() throws Exception {
                mockMvc.perform(get(AUTHORITY_PATH))
                                .andExpect(status().isOk());
        }

        @Test
        void testSaveFindByNameUpdateAndDeleteById() throws Exception {
                Authority authority = Authority.builder().build();

                // save with null name
                mockMvc.perform(post(AUTHORITY_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isBadRequest());
                // save with empty name
                authority.setName("");
                mockMvc.perform(post(AUTHORITY_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isBadRequest());
                // save
                authority.setName("test");
                mockMvc.perform(post(AUTHORITY_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isOk());
                // findByName
                mockMvc.perform(get(AUTHORITY_PATH + "/test"))
                                .andExpect(status().isOk());
                // update with null name
                authority.setName(null);
                mockMvc.perform(put(AUTHORITY_PATH + "/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isBadRequest());
                // update with empty name
                authority.setName("");
                mockMvc.perform(put(AUTHORITY_PATH + "/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isBadRequest());
                // update
                authority.setName("test2");
                mockMvc.perform(put(AUTHORITY_PATH + "/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isOk());
                // deleteById
                mockMvc.perform(delete(AUTHORITY_PATH).param("id", "1"))
                                .andExpect(status().isOk());
        }
}
