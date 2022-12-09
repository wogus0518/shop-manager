package com.palchil.shop.controller;


import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.domain.enumerate.Gender;
import com.palchil.shop.domain.enumerate.Size;
import com.palchil.shop.domain.dto.item.AddItemDto;
import com.palchil.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@Controller
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/add")
    public String addView(Model model) {
        model.addAttribute("genders", Gender.values());
        model.addAttribute("categories", Category.values());
        model.addAttribute("sizes", Size.values());
        return "item/add";
    }

    @PostMapping("/add")
    public String addItem(AddItemDto addItemDto) throws IOException {
        itemService.addItem(addItemDto);
        return "item/add";
    }

    @GetMapping("/list")
    public String itemList(Model model,
                           @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Item> items = itemService.findAll(pageable);
        log.info("number={}", items.getNumber());
        log.info("totalPages={}", items.getTotalPages());

        model.addAttribute("itemList", items);

        if(pageable.hasPrevious()) model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        if(items.hasNext()) model.addAttribute("next", pageable.next().getPageNumber());

        return "item/list";
    }
}
