package com.frankit.controller.v1.product;

import com.frankit.controller.v1.BaseControllerV1;
import com.frankit.dto.BaseResponse;
import com.frankit.dto.BaseResponseStatus;
import com.frankit.dto.product.ProductRequestDto;
import com.frankit.entity.Product;
import com.frankit.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "관리자 상품 관리 API", description = "상품 등록, 수정, 삭제, 조회 API")
@RestController
@RequestMapping(AdminProductControllerV1.PATH_NAME)
@RequiredArgsConstructor
public class AdminProductControllerV1 {
    public static final String PATH_NAME = BaseControllerV1.PATH_NAME + "/admin/products";

    public final ProductService productService;

    @Operation(summary = "상품 조회 API", description = "상품의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 조회 성공")
    })
    @GetMapping
    public ResponseEntity<BaseResponse<Page<Product>>> getProducts(
            @Parameter(description = "페이지 시작 번호", example = "0")
            @RequestParam int page,
            @Parameter(description = "한 페이지당 조회하는 사이즈", example = "10")
            @RequestParam int size
) {
        return ResponseEntity.ok(new BaseResponse<>(productService.findAllProducts(page, size)));
    }

    @Operation(summary = "상품 등록 API", description = "상품의 정보를 입력하여 상품을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 등록 성공")
    })
    @PostMapping
    public ResponseEntity<BaseResponse<BaseResponseStatus>> registerProduct(@RequestBody @Valid ProductRequestDto requestDto) {
        productService.saveProduct(requestDto.getName(), requestDto.getDescription(), requestDto.getPrice(), requestDto.getShippingCost());

        return ResponseEntity.ok(new BaseResponse<>());
    }

    @Operation(summary = "상품 삭제 API", description = "등록된 상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<BaseResponse<BaseResponseStatus>> deleteProduct(
            @Parameter(description = "상품 번호", example = "1")
            @PathVariable String productId
    ) {
        productService.deleteProduct(Long.valueOf(productId));

        return ResponseEntity.ok(new BaseResponse<>());
    }

}
