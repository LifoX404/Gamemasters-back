package com.cibertec.GameMaster.domain;

import java.math.BigDecimal;
import java.util.List;

public class Cart {

    private final List<ItemCart> items;

    public Cart(List<ItemCart> items) {
        this.items = List.copyOf(items);
    }

    public BigDecimal getTotal() {
        return items.stream()
                .map(ItemCart::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public int getTotalItems() {
        return items.stream()
                .mapToInt(ItemCart::getItemCount)
                .sum();
    }

    public List<ItemCart> getItems() {
        return items;
    }
}
