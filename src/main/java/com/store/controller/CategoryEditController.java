package com.store.controller;

import com.store.domain.Basket;
import com.store.domain.Category;
import com.store.domain.Product;
import com.store.repos.BasketRepository;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
public class CategoryEditController {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private ProductRepository productRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/categoryEdit")
    public String editProduct(@RequestParam(name="id") Integer category_id, Model model, Principal principal)
    {
        if(principal != null)
            model.addAttribute("user", principal.getName());
        Category category = categoryRepository.findById(category_id);
        model.addAttribute("category", category);

        return "categoryEdit";
    }

    @PostMapping("/categoryEdit")
    public String addEditedCategory(Integer id, String name_of_pic, MultipartFile image2, Model model) throws IOException {
        Category category = categoryRepository.findById(id);
        category.setName_of_pic(name_of_pic);

        InputStream im = new ByteArrayInputStream(image2.getBytes());
        BufferedImage bufIm = ImageIO.read(im);
        if(bufIm != null)
        {
            String uuidImage = UUID.randomUUID().toString();
            String resultImage = uuidImage + "." + image2.getOriginalFilename();

            image2.transferTo(new File(uploadPath + "/" + resultImage));

            category.setImage("img/" + resultImage);
        }

        categoryRepository.save(category);

        return "redirect:/";
    }

    @GetMapping("/categoryDelete")
    public String deleteProduct(@RequestParam(name="id") Integer id,
                                Model model) {
        Category category = categoryRepository.findById(id);
        List<Product> product = productRepository.findByCategory_id(id);
        for (Product p : product){
            List<Basket> basket = basketRepository.findByProduct_id(p.getId());
            basketRepository.deleteAll(basket);
        }
        productRepository.deleteAll(product);
        categoryRepository.delete(category);
        return "redirect:/";
    }
}
