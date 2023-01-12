package com.palchil.shop.repository;

import com.palchil.shop.domain.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByDeleted(Boolean deleted);
}
