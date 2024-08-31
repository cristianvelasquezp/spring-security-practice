package org.cristianvelasquezp.springsecuritypractice.controllers;

import org.cristianvelasquezp.springsecuritypractice.exceptions.UserEmailException;
import org.cristianvelasquezp.springsecuritypractice.models.UserRequest;
import org.cristianvelasquezp.springsecuritypractice.services.UserServices;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest user) throws UserEmailException {
        try {
            userServices.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserEmailException("User already exists");
        }
        return ResponseEntity.ok("User registered successfully");
    }

}
