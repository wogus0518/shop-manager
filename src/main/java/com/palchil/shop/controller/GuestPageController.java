package com.palchil.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/8784")
public class GuestPageController {

    @GetMapping
    public String index() {
        return "guestPage/index";
    }

    @GetMapping("/about")
    public String about() {
        return "guestPage/about";
    }

    @GetMapping("/products")
    public String products() {
        return "guestPage/products";
    }

    @GetMapping("/store")
    public String store() {
        return "guestPage/store";
    }
}
