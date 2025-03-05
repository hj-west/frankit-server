package com.frankit.dto.productOption;

import com.frankit.dto.validation.RegisterRequestValidationGroup;
import com.frankit.entity.enums.OptionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ProductOptionRequestDto {
    @Schema(description = "옵션 타입", example = "INPUT")
    @NotNull(groups = {RegisterRequestValidationGroup.class})
    @NotBlank(groups = {RegisterRequestValidationGroup.class})
    private OptionType type;

    @Schema(description = "옵션 값", example = "각인 서비스 추가")
    private String value;

    @Schema(description = "옵션 추가금", example = "1000")
    private Long optionPrice;

}
