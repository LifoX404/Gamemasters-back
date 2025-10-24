package com.cibertec.GameMaster.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class ItemCart {

    private final Long productId;
    private final String productName;
    private final BigDecimal unitPrice;
    private final int itemCount;

    public ItemCart(Long productId, String productName, BigDecimal unitPrice, int itemCount) {
        if (unitPrice.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Unit price must be >= 0");
        if (itemCount < 0) throw new IllegalArgumentException("Item count must be >= 0");

        this.productId = Objects.requireNonNull(productId, "Product ID cannot be null");
        this.productName = Objects.requireNonNull(productName, "Product name cannot be null");
        this.unitPrice = unitPrice;
        this.itemCount = itemCount;
    }

    public BigDecimal getSubtotal() {
        return this.unitPrice.multiply(BigDecimal.valueOf(itemCount));
    }

    public int getItemCount() {
        return itemCount;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public String getProductName() {
        return productName;
    }

    public Long getProductId() {
        return productId;
    }

}
