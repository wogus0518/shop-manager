package com.palchil.shop.controller;

import com.palchil.shop.domain.dto.item.OrderDeleteRequest;
import com.palchil.shop.domain.entity.CartItem;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/list")
    public String orderList(Model model, Pageable pageable) {
        Page<Order> orders = orderService.findClosedOrder(pageable);
        model.addAttribute("orders", orders);
        return "adminPage/orderList";
    }

    @GetMapping("/list/{orderId}")
    public String orderOne(Model model, @PathVariable Long orderId) {
        Order order = orderService.findOne(orderId);
        List<CartItem> cartItems = order.getCartItems();
        log.info("ca={}", cartItems.size());
        model.addAttribute("cartItems", cartItems);
        return "adminPage/orderOne";
    }

    @PostMapping("/list")
    public String selectDate(String date, Model model, Pageable pageable) {
        Page<Order> orders = orderService.findByDate(date, pageable);
        model.addAttribute("orders", orders);
        return "adminPage/orderList";
    }

    @PostMapping("/list/delete")
    public String deleteOrder(OrderDeleteRequest orderDeleteRequest) {
        orderService.delete(orderDeleteRequest);
        return "redirect:/order/list";
    }
}
