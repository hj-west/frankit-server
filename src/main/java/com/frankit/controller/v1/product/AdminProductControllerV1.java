package com.frankit.controller.v1.product;

import com.frankit.controller.v1.BaseControllerV1;
import com.frankit.dto.BaseResponse;
import com.frankit.dto.BaseResponseStatus;
import com.frankit.dto.product.ProductRequestDto;
import com.frankit.dto.productOption.ProductOptionRequestDto;
import com.frankit.dto.validation.RegisterRequestValidationGroup;
import com.frankit.entity.Product;
import com.frankit.entity.ProductOption;
import com.frankit.exception.ProductNotFoundException;
import com.frankit.exception.ProductOptionLimitExceededException;
import com.frankit.exception.ProductOptionNotFoundException;
import com.frankit.service.product.ProductService;
import com.frankit.service.productOption.ProductOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "관리자 상품 관리 API", description = "상품 등록, 수정, 삭제, 조회 API")
@RestController
@RequestMapping(AdminProductControllerV1.PATH_NAME)
@RequiredArgsConstructor
public class AdminProductControllerV1 {
    public static final String PATH_NAME = BaseControllerV1.PATH_NAME + "/admin/products";

    private final ProductService productService;
    private final ProductOptionService productOptionService;

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
    public ResponseEntity<BaseResponse<BaseResponseStatus>> registerProduct(@RequestBody @Validated(RegisterRequestValidationGroup.class) ProductRequestDto requestDto) {
        productService.saveProduct(requestDto.getName(), requestDto.getDescription(), requestDto.getPrice(), requestDto.getShippingCost());

        return ResponseEntity.ok(new BaseResponse<>());
    }

    @Operation(summary = "상품 수정 API", description = "상품의 번호와 정보를 입력하여 상품을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 수정 성공")
    })
    @PutMapping("/{productId}")
    public ResponseEntity<BaseResponse<Void>> updateProduct(
            @Parameter(description = "상품 번호", example = "1")
            @PathVariable Long productId,
            @RequestBody @Valid ProductRequestDto requestDto) {
        try {
            productService.updateProduct(productId, requestDto.getName(), requestDto.getDescription(), requestDto.getPrice(), requestDto.getShippingCost());
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.FRAN404002));
        }
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @Operation(summary = "상품 삭제 API", description = "등록된 상품을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 삭제 성공")
    })
    @DeleteMapping("/{productId}")
    public ResponseEntity<BaseResponse<Void>> deleteProduct(
            @Parameter(description = "상품 번호", example = "1")
            @PathVariable Long productId
    ) {
        try {
            productService.deleteProduct(productId);
        } catch (ProductOptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.FRAN404002));
        }
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @Operation(summary = "상품 옵션 조회 API", description = "상품의 옵션을 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 옵션 조회 성공")
    })
    @GetMapping("/{productId}/options")
    public ResponseEntity<BaseResponse<List<ProductOption>>> getProductOptions(
            @Parameter(description = "상품 번호", example = "1")
            @PathVariable Long productId
    ) {
        List<ProductOption> productOptions;
        try {
           productOptions = productOptionService.getAllProductOptions(productId);
        } catch (ProductOptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.FRAN404002));
        }
        return ResponseEntity.ok(new BaseResponse<>(productOptions));
    }

    @Operation(summary = "상품 옵션 등록 API", description = "상품의 옵션을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 옵션 등록 성공")
    })
    @PostMapping("/{productId}/options")
    public ResponseEntity<BaseResponse<Void>> registerProductOptions(
            @Parameter(description = "상품 번호", example = "1")
            @PathVariable Long productId,
            @RequestBody @Validated(RegisterRequestValidationGroup.class) ProductOptionRequestDto requestDto
    ) {
        try {
            productOptionService.saveProductOption(requestDto.getType(), requestDto.getValue(), requestDto.getOptionPrice(), productId);
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.FRAN404002));
        } catch (ProductOptionLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new BaseResponse<>(BaseResponseStatus.FRAN422001));
        }

        return ResponseEntity.ok(new BaseResponse<>());
    }

    @Operation(summary = "상품 옵션 수정 API", description = "상품의 옵션을 수정합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 옵션 수정 성공")
    })
    @PutMapping("/{productId}/options/{productOptionId}")
    public ResponseEntity<BaseResponse<Void>> updateProductOptions(
            @Parameter(description = "상품 번호", example = "1")
            @PathVariable Long productId,
            @Parameter(description = "옵션 번호", example = "1")
            @PathVariable Long productOptionId,
            @RequestBody @Valid ProductOptionRequestDto requestDto
    ) {
        try {
            productOptionService.updateProductOption(productOptionId, requestDto.getType(), requestDto.getValue(), requestDto.getOptionPrice());
        } catch (ProductOptionNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse<>(BaseResponseStatus.FRAN404003));
        }
        return ResponseEntity.ok(new BaseResponse<>());
    }

    @Operation(summary = "상품 옵션 삭제 API", description = "상품의 옵션을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "상품 옵션 삭제 성공")
    })
    @DeleteMapping("/{productId}/options/{productOptionId}")
    public ResponseEntity<BaseResponse<Void>> deleteProductOptions(
            @Parameter(description = "상품 번호", example = "1")
            @PathVariable Long productId,
            @Parameter(description = "옵션 번호", example = "1")
            @PathVariable Long productOptionId
    ) {
        productOptionService.deleteProductOption(productOptionId);
        return ResponseEntity.ok(new BaseResponse<>());
    }
}
