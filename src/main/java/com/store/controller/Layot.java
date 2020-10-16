package com.store.controller;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import com.store.domain.Role;
import com.store.domain.User;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.Set;

@ControllerAdvice
class LayoutAdvice {
    @Autowired
    UserRepository userRepository;

    @ModelAttribute("layout")
    public Mustache.Lambda layout(Principal principal, Model model) {
        if (principal != null) {
            User user = userRepository.findByUsername(principal.getName());
            Set<Role> roleAdmin = user.getRoles();
            for (Role admin : roleAdmin) {
                if (admin == Role.ADMIN) {
                    model.addAttribute("isAdmin", admin);
                }
            }
        }
        return new Layout();
    }
}

class Layout implements Mustache.Lambda {
    String body;
    @Override
    public void execute(Template.Fragment frag, Writer out) throws IOException {
        body = frag.execute();
    }
}