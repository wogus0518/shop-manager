package com.palchil.shop.repository;

import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.enumerate.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("select i from Item i where i.store = :store and i.buyName = :buyName " +
            "and i.color = :color and i.size = :size and i.unitCost = :unitCost")
    Optional<Item> findSameItem(@Param("store") String store,
                                @Param("buyName") String buyName,
                                @Param("color") String color,
                                @Param("size") Size size,
                                @Param("unitCost") Integer unitCost);
}
