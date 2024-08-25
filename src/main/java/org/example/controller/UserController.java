package org.example.controller;

import org.example.model.UserData;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  private UserService userService;

  @PostMapping
  public UserData createUser(@RequestBody UserData userData) {
    return userService.saveUser(userData);
  }

  @GetMapping("/{id}")
  public Optional<UserData> getUser(@PathVariable Long id) {
    return userService.getUserById(id);
  }

  @DeleteMapping("/{id}")
  public void deleteUser(@PathVariable Long id) {
    userService.deleteUserById(id);
  }
}
