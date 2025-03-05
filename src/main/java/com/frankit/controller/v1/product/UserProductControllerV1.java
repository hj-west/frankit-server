package com.frankit.controller.v1.product;

import com.frankit.controller.v1.BaseControllerV1;
import com.frankit.dto.BaseResponse;
import com.frankit.dto.BaseResponseStatus;
import com.frankit.entity.Product;
import com.frankit.entity.ProductOption;
import com.frankit.exception.ProductOptionNotFoundException;
import com.frankit.service.product.ProductService;
import com.frankit.service.productOption.ProductOptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "사용자 상품 API", description = "상품 조회 API")
@RestController
@RequestMapping(UserProductControllerV1.PATH_NAME)
@RequiredArgsConstructor
public class UserProductControllerV1 {
    public static final String PATH_NAME = BaseControllerV1.PATH_NAME + "/user/products";

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
}
