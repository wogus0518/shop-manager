package com.palchil.shop.service;

import com.palchil.shop.domain.dto.item.ItemDto;
import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.dto.item.AddItemDto;
import com.palchil.shop.domain.enumerate.Category;
import com.palchil.shop.repository.ItemRepository;
import com.palchil.shop.repository.specification.ItemSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final QrCodeService qrCodeService;


    @Transactional
    public void addItem(AddItemDto addItemDto) throws IOException {

        List<AddItemDto> addItemDtoList = parser(addItemDto);

        for (AddItemDto dto : addItemDtoList) {
            Item item = dto.toEntity();

            //DB에 일치하는 상품이 있는지 조회
            Optional<Item> sameItem = itemRepository.findSameItem(item.getStore(), item.getBuyName(), item.getColor(), item.getSize(), item.getUnitCost());

            //일치하는 상품이 있으면 quantity, stock 증가
            sameItem.ifPresent(i -> {
                i.addQuantity(item.getQuantity());
                i.addStock(item.getQuantity());
            });

            //없으면 새롭게 상품등록
            if (sameItem.isEmpty()) {
                Item savedItem = itemRepository.save(item);
                String base64 = qrCodeService.createQr(savedItem.getId()).toString();
                savedItem.setBase64(base64);
            }
        }
    }

    @Transactional
    public Item updateItem(ItemDto itemDto) {
        Optional<Item> optionalItem = itemRepository.findById(itemDto.getId());
        itemDto.setBase64(optionalItem.get().getBase64());
        Item item = itemDto.toEntity();
        log.info("item={}", item.toString());
        return itemRepository.save(itemDto.toEntity());
    }


    public Page<Item> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    public Page<Item> findAllWithOption(Pageable pageable, String store, String saleName, String category) {
        return itemRepository.findAll(ItemSpec.searchWith(store, saleName, category), pageable);
    }

    public Item findOne(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.get();
    }

    private List<AddItemDto> parser(AddItemDto addItemDto) {
        List<AddItemDto> list = new ArrayList<>();

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

        for (int i = 0; i < purchaseDates.length; i++) {
            list.add(new AddItemDto(purchaseDates[i], stores[i], buyNames[i], saleNames[i], colors[i],
                    categories[i], sizes[i], genders[i], quantities[i], unitCosts[i], prices[i]));
        }

        return list;
    }
}
