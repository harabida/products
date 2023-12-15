package com.alten.products.product.mapper;

import com.alten.products.product.entity.ProductRequest;
import com.alten.products.product.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product createProductRequestToProduct(ProductRequest productRequest);
}
