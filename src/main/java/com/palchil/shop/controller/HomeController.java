package com.palchil.shop.controller;

import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.domain.enumerate.Gender;
import com.palchil.shop.domain.enumerate.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping
public class HomeController {

    @GetMapping
    public String home(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null){
            return "login/login";
        }
        return "home/loginHomeV2";
    }

    @GetMapping("/sidebar")
    public String sidebar(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("sizes", Size.values());
        return "layoutFile";
    }
}
