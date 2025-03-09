package com.leo.marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

//    @GetMapping("/login")
//    public String loginPage(){
//        return "userLogin";
//    }

//    @GetMapping("/register")
//    public String registerPage(){
//        return "userRegister";
//    }

    @GetMapping("/test")
    String test(Model model) {
        model.addAttribute("something", "This is from the controller");
        return "test";
    }


}
