package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    // use UserService in Signup to authenticate user
    // create new user into User database:
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = null;

        // check if the entered username is already available, log error:
        if (!this.userService.isUsernameAvailable(user.getUsername())) {
            signupError = "This username already exists.";
        }

        // if a user is NOT available, add new user to USER table
        // return the number of the newly inserted row:
        if (signupError == null) {
            int rowsAdded = this.userService.createUser(user);

            // if the newly inserted row is less than 0, log error:
            if (rowsAdded < 0) {
                signupError = "There was an error signing you up. Please try again!";
            }
        }

        // display signupSuccess and signupError logs in signup.html:
        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}