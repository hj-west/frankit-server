package com.frankit.controller.v1.product;

import com.frankit.controller.v1.BaseControllerV1;
import com.frankit.dto.BaseResponse;
import com.frankit.dto.BaseResponseStatus;
import com.frankit.dto.product.RegisterProductRequestDto;
import com.frankit.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 상품 관리 API", description = "상품 등록, 수정, 삭제, 조회 API")
@RestController
@RequestMapping(AdminProductControllerV1.PATH_NAME)
@RequiredArgsConstructor
public class AdminProductControllerV1 {
    public static final String PATH_NAME = BaseControllerV1.PATH_NAME + "/admin/products";

    public final ProductService productService;

    @Operation(summary = "상품 등록 API", description = "상품의 정보를 입력하여 상품을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 등록 성공")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<BaseResponseStatus>> registerProduct(@RequestBody @Valid RegisterProductRequestDto requestDto) {
        productService.saveProduct(requestDto.getName(), requestDto.getDescription(), requestDto.getPrice(), requestDto.getShippingCost());

        return ResponseEntity.ok(new BaseResponse<>());
    }


}
