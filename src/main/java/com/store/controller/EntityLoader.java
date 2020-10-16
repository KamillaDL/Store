package com.store.controller;

import com.store.domain.Role;
import com.store.domain.User;
import com.store.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;

@Component
public class EntityLoader {
    @Autowired
    UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @PostConstruct
    public void DoInitialization(){
        if(userRepository.findByRoles(Role.ADMIN).size() == 0) {
            User user = new User();
            user.setEmail("admin@mail.ru");
            user.setRoles(Collections.singleton(Role.ADMIN));
            user.setUsername("admin");
            user.setPassword("admin");
            userRepository.save(user);
        }

        Path path = Paths.get(uploadPath);
        if (!Files.exists(path)) {
            new File(uploadPath).mkdir();
        }

    }
}
