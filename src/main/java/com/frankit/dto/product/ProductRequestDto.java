package com.frankit.dto.product;

import com.frankit.dto.validation.RegisterRequestValidationGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductRequestDto {
    @Schema(description = "상품명", example = "모나미 볼펜")
    @NotNull(groups = {RegisterRequestValidationGroup.class})
    @NotBlank(groups = {RegisterRequestValidationGroup.class})
    private String name;

    @Schema(description = "상품 설명", example = "기본에 춤실한 볼펜")
    private String description;

    @Schema(description = "상품 가격", example = "1000")
    @NotNull(groups = {RegisterRequestValidationGroup.class})
    private Long price;

    @Schema(description = "배송비", example = "500")
    private Long shippingCost;
}
