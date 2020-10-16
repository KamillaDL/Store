package com.store.controller;

import com.store.domain.*;
import com.store.repos.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class BasketController {
    @Autowired
    private BasketRepository basketRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DetailsRepository detailsRepository;

    @GetMapping("/basket")
    public String show_basket(Principal principal, Map<String, Object> model) {
        User user = userRepository.findByUsername(principal.getName());
        List<Basket> products_in_basket = basketRepository.findByUser_id(user.getId());
        model.put("products_in_basket", products_in_basket);
        if(!products_in_basket.isEmpty()){
            model.put("have", "asd");
        }
        if (principal != null)
            model.put("user", principal.getName());
        return "products_in_basket";
    }

    @GetMapping("/changeCount")
    public String changeCount(boolean sign, Integer id) {
        Basket basket = basketRepository.findById(id).get();
        Long v = basket.getValue();
        if (sign == true)
            v += 1;
        else
            v -= 1;
        basket.setValue(v);
        basketRepository.save(basket);
        return "redirect:/basket";
    }

    @PostMapping("/buy")
    public String buy_product(Integer product_id, Model model) {
        if (product_id != 0) {
            model.addAttribute("product_id", product_id);
        }
        return "buy";
    }

    @PostMapping("/buyItem")
    public void buyItem(Details details, Integer product_id, Principal principal, Model model, HttpServletResponse response) throws IOException {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph thank = doc.createParagraph();
        XWPFRun thankRun = thank.createRun();
        thankRun.setText("Спасибо за покупку!");

        Buy buy = new Buy();
        List<Basket> basket = null;

        User user = userRepository.findByUsername(principal.getName());

        if (product_id != null) {
            Product product = productRepository.findById(product_id);
            buy.setProduct(product);
            basket = basketRepository.findByProduct_idAndUser_id(product_id, user.getId());
        } else {
            basket = basketRepository.findByUser_id(user.getId());
        }

        details.setUser(user);
        detailsRepository.save(details);

        XWPFParagraph name = doc.createParagraph();
        XWPFRun nameRun = name.createRun();
        nameRun.setText("Ваше имя - " + details.getName() + " " + details.getSurname());

        Long summ = 0L;

        for (Basket b : basket) {
            buy = new Buy();
            Product product = b.getProduct();
            buy.setProduct(product);
            buy.setDetails(details);
            buy.setValue(b.getValue());
            buyRepository.save(buy);
            basketRepository.deleteById(b.getId());

            XWPFParagraph nameOfItem = doc.createParagraph();
            XWPFRun nameOfItemRun = nameOfItem.createRun();
            nameOfItemRun.setText("Название продукта - " + product.getName_of_item());

            XWPFParagraph price = doc.createParagraph();
            XWPFRun priceRun = price.createRun();
            priceRun.setText("Цена за единицу продукта - " + product.getPrice());
            summ += product.getPrice() * b.getValue();

            XWPFParagraph value = doc.createParagraph();
            XWPFRun valueRun = value.createRun();
            valueRun.setText("Количество - " + b.getValue());
        }

        XWPFParagraph itog = doc.createParagraph();
        XWPFRun itogRun = itog.createRun();
        itogRun.setText("Общая стоимость - " + summ);

        response.setHeader("Content-disposition", "attachment; filename=order.docx");
        doc.write(response.getOutputStream());
    }

    @PostMapping("/remove")
    public String delete_from_basket(Integer id, Map<String, Object> model) {
        basketRepository.deleteById(id);
        return "redirect:/basket";
    }
}


