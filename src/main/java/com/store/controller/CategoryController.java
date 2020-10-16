package com.store.controller;

import com.store.domain.Basket;
import com.store.domain.Product;
import com.store.domain.Role;
import com.store.domain.User;
import com.store.repos.BasketRepository;
import com.store.repos.ProductRepository;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class CategoryController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/category")
    public String show_items(@RequestParam(name = "id") Integer category_id, Principal principal, Model model) {
        try {
            Iterable<Product> products = productRepository.findByCategory_id(category_id);
            model.addAttribute("products", products);
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            if (name != null) {
                User user = userRepository.findByUsername(name);
                Set<Role> roleAdmin = user.getRoles();
                for (Role admin : roleAdmin) {
                    if (admin == Role.ADMIN) {
                        model.addAttribute("isAdmin", admin);
                    }
                }
            }
        }
        catch (NullPointerException e){
            model.addAttribute("user", null);
        }
        if (principal != null)
            model.addAttribute("user", principal.getName());
        return "products";
    }

    @PostMapping("/add_to_basket")
    public ResponseEntity<Void> add_to_basket(Integer product_id, Integer category_id, Long number, Principal principal, Map<String, Object> model) throws Exception {
        List<Basket> b = basketRepository.findByProduct_id(product_id);
        for (Basket bb : b) {
            if (bb.getUser() == userRepository.findByUsername(principal.getName())) {
                bb.setValue(bb.getValue() + 1);
                basketRepository.save(bb);
                return ResponseEntity.noContent().<Void>build();
            }
        }
        Basket basket = new Basket();
        basket.setProduct(productRepository.findById(product_id));
        basket.setUser(userRepository.findByUsername(principal.getName()));
        basket.setValue(number);
        basketRepository.save(basket);
        return ResponseEntity.noContent().<Void>build();
    }
}
