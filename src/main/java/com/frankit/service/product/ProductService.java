package com.frankit.service.product;

public interface ProductService {
    void saveProduct(String name, String description, Long price, Long shippingCost);
}
