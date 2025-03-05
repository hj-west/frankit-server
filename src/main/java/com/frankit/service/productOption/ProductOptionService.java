package com.frankit.service.productOption;

import com.frankit.entity.ProductOption;
import com.frankit.entity.enums.OptionType;

import java.util.List;

public interface ProductOptionService {
    List<ProductOption> getAllProductOptions(Long productId);
    void saveProductOption(OptionType optionType, String value, Long optionPrice, Long productId);
    void updateProductOption(Long productOptionId, OptionType optionType, String value, Long optionPrice);
    void deleteProductOption(Long productOptionId);
}
