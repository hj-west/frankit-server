package com.frankit.service.productOption;

import com.frankit.entity.Product;
import com.frankit.entity.ProductOption;
import com.frankit.entity.enums.OptionType;
import com.frankit.exception.ProductNotFoundException;
import com.frankit.exception.ProductOptionLimitExceededException;
import com.frankit.exception.ProductOptionNotFoundException;
import com.frankit.repository.ProductOptionRepository;
import com.frankit.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class ProductOptionServiceImpl implements ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    @Override
    public List<ProductOption> getAllProductOptions(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

        return productOptionRepository.findByProduct(product);
    }

    @Override
    @Transactional
    public void saveProductOption(OptionType optionType, String value, Long optionPrice, Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found: " + productId));

        long optionCount = productOptionRepository.countByProduct(product);
        if (optionCount >= 3) {
            throw new ProductOptionLimitExceededException("A product cannot have more than 3 options.");
        }

        productOptionRepository.save(ProductOption.builder()
                .type(optionType)
                .value(value)
                .optionPrice(optionPrice)
                .product(product)
                .build());
    }

    @Override
    @Transactional
    public void updateProductOption(Long productOptionId, OptionType optionType, String value, Long optionPrice) {
        ProductOption productOption = productOptionRepository.findById(productOptionId)
                .orElseThrow(() -> new ProductOptionNotFoundException("Product Option not found: " + productOptionId));

        Optional.ofNullable(optionType).ifPresent(productOption::setType);
        Optional.ofNullable(value).ifPresent(productOption::setValue);
        Optional.ofNullable(optionPrice).ifPresent(productOption::setOptionPrice);
    }

    @Override
    @Transactional
    public void deleteProductOption(Long productOptionId) {
        if (!productOptionRepository.existsById(productOptionId)) {
            throw new ProductOptionNotFoundException("Product Option not found: " + productOptionId);
        }
        productOptionRepository.deleteById(productOptionId);
    }
}
