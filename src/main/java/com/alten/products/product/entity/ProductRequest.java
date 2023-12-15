package com.alten.products.product.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        String code,
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotNull
        @Min(0)
        BigDecimal price,
        @NotNull
        @Min(0)
        Integer quantity,
        //TODO replace with an ENUM
        @NotBlank
        String inventoryStatus,
        @NotBlank
        String category,
        String image,
        Integer rating
) {
}
