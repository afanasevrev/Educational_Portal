package com.example.Educational_Portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
/**
 * Контроллер - отвечает на запросы пользователей через браузер
 */
@Controller
public class ControllerHome {
    @GetMapping("/")
    private String getInfo() {
        return "home_page";
    }
}
