package com.store.controller;
import com.store.domain.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class FailLoginController {
    @GetMapping("/loginError")
    public String fail_log(Map<String,Object> model) {
        model.put("msg", "Неверный логин или пароль");
        return "login";
    }
}
