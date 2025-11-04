package com.tecsup.app.micro.user.controller;

import com.tecsup.app.micro.user.dto.User;
import com.tecsup.app.micro.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<User> getAllUsers() {
        log.info("REST request to get all users");
        return userService.getAllUsers();
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        log.info("REST request to get user by id: {}", id);
        return ResponseEntity.ok(userService.getUserById(id));
    }


}
