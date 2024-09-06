package org.cristianvelasquezp.springsecuritypractice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model,
                        @RequestParam(value = "error", required = false) Boolean error,
                        @RequestParam(value = "logout", required = false) Boolean logout) {

        String errorMessage = null;

        if (Boolean.TRUE.equals(error)) {
            errorMessage = "Username or Password is incorrect !!";
        }

        if (Boolean.TRUE.equals(logout)) {
            errorMessage = "You have been successfully logged out !!";
        }

        model.addAttribute("errorMessge", errorMessage);

        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
