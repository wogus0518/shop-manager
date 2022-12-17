package com.palchil.shop.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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
        log.info(request.getServerName());
        HttpSession session = request.getSession(false);
        if(session == null){
            return "adminPage/loginPage";
        }
        return "adminPage/main";
    }
}
