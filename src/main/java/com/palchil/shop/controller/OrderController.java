package com.palchil.shop.controller;

import com.palchil.shop.domain.entity.Order;
import com.palchil.shop.service.ItemService;
import com.palchil.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
