package com.palchil.shop.controller;

import com.palchil.shop.domain.dto.item.CalculateDto;
import com.palchil.shop.domain.entity.CartItem;
import com.palchil.shop.service.CartItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartItemService cartItemService;

    @GetMapping
    public String cartView(Model model) {
        List<CartItem> cartItemList = cartItemService.getCartItemList();

        int totalPrice = 0;
        for (CartItem cartItem : cartItemList) {
            int orderPrice = cartItem.getOrderPrice();
            int count = cartItem.getCount();
            totalPrice += orderPrice * count;
        }

        model.addAttribute("itemList", cartItemList);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("change", "0");
        return "adminPage/cart";
    }

    //계산 완료, 판매 요청
    @PostMapping
    public String calculated(Model model, CalculateDto calculateDto) {
        cartItemService.order();
        List<CartItem> cartItemList = cartItemService.getCartItemList();
        model.addAttribute("itemList", cartItemList);
        model.addAttribute("totalPrice", "0");
        model.addAttribute("change", calculateDto.getReceived() - Integer.parseInt(calculateDto.getTotalPrice()));
        return "adminPage/cart";
    }
}
