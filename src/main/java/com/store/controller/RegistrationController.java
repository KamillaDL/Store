package com.store.controller;

import com.store.domain.Role;
import com.store.domain.User;
import com.store.repos.UserRepository;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping ("/registration")
    public String registration()
    {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Model model)
    {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if(userFromDb != null)
        {
            model.addAttribute("usernameError", "Этот логин уже занят!");
            return "registration";
        }

        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/login";
    }
}
