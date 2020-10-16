package com.store.controller;

import com.store.domain.Category;
import com.store.domain.Product;
import com.store.repos.CategoryRepository;
import com.store.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class AddProductController {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/addProduct")
    public String addProduct(Principal principal, Model model){
        if(principal != null)
            model.addAttribute("user", principal.getName());
        List<Category> cat = categoryRepository.findAll();
        model.addAttribute("cat", cat);
        return "addProductForm";
    }

    @PostMapping("/addProduct")
    public String addingProduct(Product product, String name_of_category, Model model, MultipartFile image2) throws IOException {
        Category category = categoryRepository.findCategoryByName_of_pic(name_of_category);
        product.setCategory(category);
        if(image2 != null)
        {
            String uuidImage = UUID.randomUUID().toString();
            String resultImage = uuidImage + "." + image2.getOriginalFilename();

            image2.transferTo(new File(uploadPath + "/" + resultImage));

            product.setImage("img/" + resultImage);
        }
        productRepository.save(product);
        return "redirect:/addProduct";
    }
}
