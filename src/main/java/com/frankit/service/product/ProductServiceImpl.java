package com.frankit.service.product;

import com.frankit.entity.Product;
import com.frankit.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Page<Product> findAllProducts(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

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

    @Override
    @Transactional
    public void updateProduct(Long productId, String name, String description, Long price, Long shippingCost) throws BadRequestException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new BadRequestException("Product not found :" + productId));

        // 파라미터가 아예 넘어오지 않는 경우도 고려함
        product.setName(Optional.ofNullable(name).orElse(product.getName()));
        product.setDescription(Optional.ofNullable(description).orElse(product.getDescription()));
        product.setPrice(Optional.ofNullable(price).orElse(product.getPrice()));
        product.setShippingCost(Optional.ofNullable(shippingCost).orElse(product.getShippingCost()));

        productRepository.save(product);
    }

    @Override
    @Transactional
    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }
}
