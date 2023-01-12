package com.palchil.shop.repository;

import com.palchil.shop.domain.entity.Order;
import com.palchil.shop.domain.enumerate.OrderType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<Order> findByOrderType(OrderType orderType);

    Page<Order> findByOrderType(OrderType orderType, Pageable pageable);
}
