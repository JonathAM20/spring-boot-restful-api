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

        @Test
        void testDeleteById() throws Exception {
                mockMvc.perform(delete("/authority").param("id", "1"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testUpdate() throws Exception {
                Authority authority = Authority.builder().name("test2").build();

                mockMvc.perform(put("/authority/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindByName() throws Exception {
                mockMvc.perform(get("/authority/test"))
                                .andExpect(status().isBadRequest());
        }

        @Test
        void testFindAll() throws Exception {
                mockMvc.perform(get("/authority"))
                                .andExpect(status().isOk());
        }

        @Test
        void testSaveFindByNameUpdateAndDeleteById() throws Exception {
                Authority authority = Authority.builder().name("test").build();

                // save
                mockMvc.perform(post("/authority")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isOk());
                // findByName
                mockMvc.perform(get("/authority/test"))
                                .andExpect(status().isOk());
                // update
                authority.setName("test2");
                mockMvc.perform(put("/authority/test")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(authority)))
                                .andExpect(status().isOk());
                // deleteById
                mockMvc.perform(delete("/authority").param("id", "1"))
                                .andExpect(status().isOk());
        }
}
