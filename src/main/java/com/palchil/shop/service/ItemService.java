package com.palchil.shop.service;

import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.dto.item.AddItemDto;
import com.palchil.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final QrCodeService qrCodeService;

    @Transactional
    public void addItem(AddItemDto addItemDto) throws IOException {
        String[] purchaseDates = addItemDto.getPurchaseDate().split(",");
        String[] stores = addItemDto.getStore().split(",");
        String[] buyNames = addItemDto.getBuyName().split(",");
        String[] saleNames = addItemDto.getSaleName().split(",");
        String[] colors = addItemDto.getColor().split(",");
        String[] categories = addItemDto.getCategory().split(",");
        String[] sizes = addItemDto.getSize().split(",");
        String[] genders = addItemDto.getGender().split(",");
        String[] quantities = addItemDto.getQuantity().split(",");
        String[] unitCosts = addItemDto.getUnitCost().split(",");
        String[] prices = addItemDto.getPrice().split(",");

        for (int i = 0; i < stores.length; i++) {
            Item item = itemRepository.save(new AddItemDto(purchaseDates[i], stores[i], buyNames[i], saleNames[i], colors[i], categories[i], sizes[i], genders[i], quantities[i], unitCosts[i], prices[i]).toEntity());
            String base64 = qrCodeService.createQr(item.getId()).toString();
            item.setBase64(base64);
        }
    }
}
