package com.palchil.shop.controller;


import com.palchil.shop.domain.dto.item.ItemDto;
import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.entity.Order;
import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.domain.enumerate.Gender;
import com.palchil.shop.domain.enumerate.Size;
import com.palchil.shop.domain.dto.item.AddItemDto;
import com.palchil.shop.service.ItemService;
import com.palchil.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/add")
    public String addView(Model model) {
        addAttributeEnum(model);
        return "adminPage/itemAdd";
    }

    @PostMapping("/add")
    public String addItem(AddItemDto addItemDto) throws IOException {
        itemService.addItem(addItemDto);
        return "adminPage/itemAdd";
    }

    @GetMapping("/list")
    public String itemList(Model model, Pageable pageable,
                           @RequestParam(required = false) String saleName,
                           @RequestParam(required = false) String store,
                           @RequestParam(required = false) String category) {
        Page<Item> items = itemService.findAllWithOption(pageable, store, saleName, category);
        model.addAttribute("itemList", items);

        if(pageable.hasPrevious()) model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        if(items.hasNext()) model.addAttribute("next", pageable.next().getPageNumber());

        return "adminPage/itemList";
    }

    //상점명, 판매명으로 검색하는 POST 요청이 왔을 때
    @PostMapping("/list")
    public String search(@RequestParam(required = false) String saleName,
                         @RequestParam(required = false) String store,
                         RedirectAttributes redirectAttributes) {
        if(!saleName.isEmpty()) redirectAttributes.addAttribute("saleName", saleName);
        if(!store.isEmpty()) redirectAttributes.addAttribute("store", store);
        return "redirect:/item/list";
    }

    @GetMapping("/list/{id}")
    public String itemOne(@PathVariable Long id, Model model) {
        Item item = itemService.findOne(id);
        model.addAttribute("item", item.toDto());
        return "adminPage/itemOne";
    }

    @GetMapping("/modify/{id}")
    public String itemModify(@PathVariable Long id, Model model) {
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);

        addAttributeEnum(model);

        return "adminPage/itemUpdate";
    }

    @GetMapping("/order/{id}")
    public String itemOrder(@PathVariable Long id, Model model) {
        orderService.order(id);
        Item item = itemService.findOne(id);
        model.addAttribute("item", item);

        addAttributeEnum(model);

        return "redirect:/item/list/" + id;
    }

    @PostMapping("/modify/{id}")
    public String updateItem(@PathVariable Long id, ItemDto itemDto) {
        itemDto.setId(id);
        itemService.updateItem(itemDto);

        return "redirect:/item/list/" + id;
    }

    /**
     * 편의 메서드
     */

    //thymeleaf에서 필요한 Enum을 Model에 넣어주는 메서드
    private static void addAttributeEnum(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("sizes", Size.values());
    }
}
