package com.store.controller;

import com.store.domain.Category;
import com.store.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/")
    public String index(Principal principal, Map<String,Object> model) {
        if(principal != null)
            model.put("user", principal.getName());
        return "index";
    }

    @GetMapping("/categories")
    public String categories(Principal principal, Map<String,Object> model) {

        Iterable<Category> categories = categoryRepository.findAll();
        model.put("categories", categories);
        if(principal != null)
            model.put("user", principal.getName());
        return "categories";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(){
        return "aboutUs";
    }

}