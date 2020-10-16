package com.store.controller;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    ProductRepository productRepository;

    @PostMapping("/search")
    public String searchProduct(String name_of_item, Model model, Principal principal){
        if(principal != null)
            model.addAttribute("user", principal.getName());
        List<Product> products = productRepository.findProductsByName_of_item(name_of_item);
        model.addAttribute("products", products);
        model.addAttribute("isSearch", "asd");
        return "products";
    }
}
