package com.frankit.service.product;

import com.frankit.entity.Product;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<Product> findAllProducts(int page, int size);
    void saveProduct(String name, String description, Long price, Long shippingCost);
    void updateProduct(Long productId, String name, String description, Long price, Long shippingCost);
    void deleteProduct(Long productId);
}
