package com.example.defender;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyController {
    @GetMapping("/attack")
    public String getAttacker(){
        return "attack";
    }
}
