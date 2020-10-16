package com.store.controller;

import com.store.domain.Buy;
import com.store.repos.BuyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class AdminController {
    @Autowired
    BuyRepository buyRepository;

    @GetMapping("/adminPanel")
    public String adminPanel(Principal principal, Model model){
        if(principal != null)
            model.addAttribute("user", principal.getName());
        Iterable<Buy> orders =  buyRepository.findAll();
        model.addAttribute("orders", orders);
        return "adminPanel";
    }
}
