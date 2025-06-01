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

import com.project.spring_boot_restful_api.model.Role;
import com.project.spring_boot_restful_api.service.RoleService;

@RestController
@RequestMapping("role")
public class RoleController {

    private RoleService roleService;

    public RoleController(final RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Role role) {
        roleService.save(role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("{name}")
    public ResponseEntity<Void> update(@PathVariable String name, @RequestBody Role role) {
        roleService.update(name, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Role>> findAll() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<Role> findByName(@PathVariable String name) {
        return new ResponseEntity<>(roleService.findByName(name), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(@RequestParam Long id) {
        roleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
