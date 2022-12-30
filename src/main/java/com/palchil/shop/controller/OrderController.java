package com.palchil.shop.controller;

import com.palchil.shop.domain.entity.Order;
import com.palchil.shop.service.ItemService;
import com.palchil.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public String orderList(Model model, Pageable pageable) {
        Page<Order> orders = orderService.findAll(pageable);
        model.addAttribute("orders", orders);
        return "adminPage/orderList";
    }

    @PostMapping("/list")
    public String selectDate(String date, Model model, Pageable pageable) {
        Page<Order> orders = orderService.findByDate(date, pageable);
        model.addAttribute("orders", orders);
        return "adminPage/orderList";
    }
}
