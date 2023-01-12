package com.palchil.shop.service;

import com.palchil.shop.domain.dto.item.OrderDeleteRequest;
import com.palchil.shop.domain.dto.item.SaleDto;
import com.palchil.shop.domain.entity.CartItem;
import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.entity.Order;
import com.palchil.shop.domain.enumerate.OrderType;
import com.palchil.shop.repository.ItemRepository;
import com.palchil.shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;
    private final ItemRepository itemRepository;

//    @Transactional
//    public void order(Long id, SaleDto saleDto) {
//        Item item = itemService.findOne(id);
//
//        int count = Integer.parseInt(saleDto.getSaleQuantity());
//        int orderPrice = 0;
//
//        if (saleDto.getSaleSort().equals("percentage")) {
//            double saleNum = (long)(100-Integer.parseInt(saleDto.getSaleAmount()))/100.0;
//            orderPrice = (int) (item.getPrice() * saleNum);
//        } else if (saleDto.getSaleSort().equals("fix")) {
//            orderPrice = item.getPrice() - Integer.parseInt(saleDto.getSaleAmount());
//        } else {
//            orderPrice = item.getPrice();
//        }
//
//        //주문상품 생성
//        CartItem cartItem = CartItem.createCartItem(item, orderPrice, count);
//        //주문 생성
//        Order order = Order.createOrder(cartItem);
//
//        //주문 저장
//        orderRepository.save(order);
//    }

    @Transactional
    public void delete(OrderDeleteRequest orderDeleteRequest) {
        Long orderId = Long.parseLong(orderDeleteRequest.getOrderId());
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order가 존재하지 않습니다."));
        orderRepository.delete(order);
    }
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Order findOne(Long id) {
        return orderRepository.findById(id).get();
    }
    public Page<Order> findClosedOrder(Pageable pageable) {
        return orderRepository.findByOrderType(OrderType.CLOSED, pageable);
    }
    public Page<Order> findByDate(String date, Pageable pageable) {
        String[] dates = date.split("/");
        LocalDate newDate = LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]), Integer.parseInt(dates[1]));
        System.out.println("newDate = " + newDate);
        return orderRepository.findByOrderDateBetween(LocalDateTime.now(), LocalDateTime.now(), pageable);
    }

    /**
     * OrderType.PROCESS 인 order를 반환
     * 검색해서 없다면 만들어서 반환
     */
    public Order getOrder() {
        Optional<Order> optionalOrder = orderRepository.findByOrderType(OrderType.PROCESSING);
        if (optionalOrder.isPresent()) {
            return optionalOrder.get();
        } else {
            Order order = Order.createOrder();
            orderRepository.save(order);
            return order;
        }
    }

    @Transactional
    public void updateTotalPriceAndCount(Order order, int orderPrice, int count) {
        int totalPrice = order.getTotalPrice() + orderPrice * count;
        int totalCount = order.getTotalCount() + count;

        order.setTotalPrice(totalPrice);
        order.setTotalCount(totalCount);
    }
}
