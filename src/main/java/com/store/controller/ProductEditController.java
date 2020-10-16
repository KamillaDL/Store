package com.store.controller;

import com.store.domain.Basket;
import com.store.domain.Product;
import com.store.repos.BasketRepository;
import com.store.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class ProductEditController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BasketRepository basketRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/productEdit")
    public String editProduct(@RequestParam(name="id") Integer product_id, Model model)
    {
        Product product = productRepository.findById(product_id);
        model.addAttribute("product", product);

        return "productEdit";
    }

    @PostMapping("/productEdit")
    public String addEditedProduct(@RequestParam(name="id") Integer product_id, String name, String description, Integer price,
                                   MultipartFile image2,
                                   Integer category_id,
                                   Model model) throws IOException {
        Product product = productRepository.findById(product_id);
        product.setName_of_item(name);
        product.setDescription(description);
        product.setPrice(price);
        InputStream im = new ByteArrayInputStream(image2.getBytes());
        BufferedImage bufIm = ImageIO.read(im);
        if(bufIm != null)
        {
            String uuidImage = UUID.randomUUID().toString();
            String resultImage = uuidImage + "." + image2.getOriginalFilename();

            image2.transferTo(new File(uploadPath + "/" + resultImage));

            product.setImage("img/" + resultImage);
        }

        productRepository.save(product);

        return "redirect:/category?id="+category_id;
    }

    @GetMapping("/productDelete")
    public String deleteProduct(@RequestParam(name="id") Integer product_id,
                                   Model model) {
        Product product = productRepository.findById(product_id);
        List <Basket> basket = basketRepository.findByProduct_id(product_id);
        basketRepository.deleteAll(basket);
        productRepository.delete(product);
        return "redirect:/category?id=" + product.getCategory().getId();
    }
}
