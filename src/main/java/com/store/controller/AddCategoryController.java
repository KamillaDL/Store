package com.store.controller;

import com.store.domain.Category;
import com.store.repos.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.UUID;

@Controller
public class AddCategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/addCategory")
    public String addCategory(Principal principal, Model model){
        if(principal != null)
            model.addAttribute("user", principal.getName());
        return "addCategoryForm";
    }

    @PostMapping("/addCategory")
    public String addingCategory(Category category, MultipartFile image2, Model model) throws IOException{
        if(image2 != null)
        {
            String uuidImage = UUID.randomUUID().toString();
            String resultImage = uuidImage + "." + image2.getOriginalFilename();

            image2.transferTo(new File(uploadPath + "/" + resultImage));

            category.setImage("img/" + resultImage);
        }
        categoryRepository.save(category);
        return "redirect:/addCategory";
    }
}
