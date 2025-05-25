package com.project.spring_boot_restful_api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.project.spring_boot_restful_api.model.Authority;
import com.project.spring_boot_restful_api.service.AuthorityService;

@RestController
@RequestMapping("authority")
public class AuthorityController {

    private AuthorityService authorityService;

    public AuthorityController(final AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Authority authority) {
        authorityService.save(authority);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestBody Authority authority) {
        authorityService.update(name, authority);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Authority>> findAll() {
        return new ResponseEntity<>(authorityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<Authority> findByName(@PathVariable String name) {
        return new ResponseEntity<>(authorityService.findByName(name), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        authorityService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
