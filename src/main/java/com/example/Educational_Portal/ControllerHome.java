package com.example.Educational_Portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ControllerHome {
    @GetMapping("/")
    private String getInfo() {
        return "Образовательный портал";
    }
}
