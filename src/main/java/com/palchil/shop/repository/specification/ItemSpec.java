package com.palchil.shop.repository.specification;

import com.palchil.shop.domain.entity.Item;
import com.palchil.shop.domain.enumerate.Category;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class ItemSpec {

    public static Specification<Item> searchWith(final String store, final String saleName, final String category) {
        return (((root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(store)) {
                predicates.add(builder.like(root.get("store"), "%" + store + "%"));
            }
            if (StringUtils.hasText(saleName)) {
                predicates.add(builder.like(root.get("saleName"), "%" + saleName + "%"));
            }
            if (StringUtils.hasText(category)) {
                predicates.add(builder.equal(root.get("category"), Category.valueOf(category)));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        }));
    }
}
