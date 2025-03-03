package com.frankit.service.product;

import com.frankit.entity.Product;
import com.frankit.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public void saveProduct(String name, String description, Long price, Long shippingCost) {
        productRepository.save(Product.builder()
                .name(name)
                .description(description)
                .price(price)
                .shippingCost(shippingCost)
                .build());
    }
}
