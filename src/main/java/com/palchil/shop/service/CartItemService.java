package com.palchil.shop.service;

import com.palchil.shop.domain.dto.item.SaleDto;
import com.palchil.shop.domain.entity.CartItem;
import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.entity.Order;
import com.palchil.shop.repository.CartItemRepository;
import com.palchil.shop.repository.ItemRepository;
import com.palchil.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartItemService {

    private final OrderService orderService;
    private final CartItemRepository cartItemRepository;
    private final ItemService itemService;

    /**
     * 장바구니에 물건을 추가합니다.
     */
    @Transactional
    public void add(Long itemId, SaleDto saleDto) {
        Item item = itemService.findOne(itemId);
        //주문 가져오기
        Order order = orderService.getOrder();

        int orderPrice = calculateOrderPrice(saleDto, item);
        int count = Integer.parseInt(saleDto.getSaleQuantity());

        CartItem cartItem = CartItem.createCartItem(item, order, orderPrice, count);

        orderService.updateTotalPriceAndCount(order, orderPrice, count);
        cartItemRepository.save(cartItem);
    }

    public List<CartItem> getCartItemList() {
        return cartItemRepository.findAllByDeleted(Boolean.FALSE);
    }

    @Transactional
    public void order() {
        //주문 완료 상태 변경
        Order order = orderService.getOrder();
        order.closed();

        //장바구니에 있는 리스트 전부 삭제하고
        cartItemRepository.deleteAll();
    }

    private static int calculateOrderPrice(SaleDto saleDto, Item item) {
        int orderPrice;
        if (saleDto.getSaleSort().equals("percentage")) {
            double saleNum = (long)(100-Integer.parseInt(saleDto.getSaleAmount()))/100.0;
            orderPrice = (int) (item.getPrice() * saleNum);
        } else if (saleDto.getSaleSort().equals("fix")) {
            orderPrice = item.getPrice() - Integer.parseInt(saleDto.getSaleAmount());
        } else {
            orderPrice = item.getPrice();
        }
        return orderPrice;
    }
}
