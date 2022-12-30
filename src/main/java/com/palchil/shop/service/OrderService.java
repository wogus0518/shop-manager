package com.palchil.shop.service;

import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.entity.Order;
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
    private final ItemRepository itemRepository;

    @Transactional
    public void order(Long id) {
        Optional<Item> item = itemRepository.findById(id);
        item.ifPresent(orderItem -> {
            orderItem.removeStock(1);
            Order order = Order.createOrder(orderItem);
            orderRepository.save(order);
        });
    }

    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Page<Order> findByDate(String date, Pageable pageable) {
        String[] dates = date.split("/");
        LocalDate newDate = LocalDate.of(Integer.parseInt(dates[2]), Integer.parseInt(dates[0]), Integer.parseInt(dates[1]));
        System.out.println("newDate = " + newDate);
        return orderRepository.findByOrderDateContaining(newDate, pageable);
    }
}
