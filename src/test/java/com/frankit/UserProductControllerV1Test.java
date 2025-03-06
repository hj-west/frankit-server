package com.frankit;
import com.frankit.controller.v1.product.UserProductControllerV1;
import com.frankit.entity.Product;
import com.frankit.entity.ProductOption;
import com.frankit.entity.enums.OptionType;
import com.frankit.repository.ProductRepository;
import com.frankit.service.product.ProductService;
import com.frankit.service.productOption.ProductOptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // ✅ Mockito 기반 테스트
public class UserProductControllerV1Test {

    private MockMvc mockMvc;

    @Mock // ✅ Mock Repository
    private ProductRepository productRepository;

    @Mock // ✅ Mock Service Layer
    private ProductService productService;

    @Mock
    private ProductOptionService productOptionService;

    @InjectMocks // ✅ 컨트롤러에 Mock된 서비스 주입
    private UserProductControllerV1 userProductControllerV1;

    @BeforeEach
    void setUp() {
        // ✅ Spring Context 없이 MockMvc 직접 빌드
        mockMvc = MockMvcBuilders
                .standaloneSetup(userProductControllerV1)
                .build();
    }

    @DisplayName("사용자 상품 조회 API 테스트")
    @Test
    void getAllProducts() throws Exception {
        // given (실제 데이터 생성)
        Product product1 = Product.builder()
                .productId(0L)
                .name("볼펜")
                .price(10000L)
                .shippingCost(2500L)
                .description("예쁜 볼펜")
                .build();

        Product product2 = Product.builder()
                .productId(1L)
                .name("모자")
                .price(30000L)
                .shippingCost(2500L)
                .description("너무 괜찮은 모자")
                .build();

        List<Product> productList = Arrays.asList(product1, product2);
        Page<Product> mockPage = new PageImpl<>(productList, PageRequest.of(0, 10), productList.size());

        when(productService.findAllProducts(anyInt(), anyInt())).thenReturn(mockPage);

        // when
        MvcResult result = mockMvc.perform(get(UserProductControllerV1.PATH_NAME) // ✅ 직접 URL 입력
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("볼펜");
    }

    @DisplayName("사용자 상품 옵션 조회 API 테스트")
    @Test
    void getAllProductOptions() throws Exception {
        // given (실제 데이터 생성)
        Product product1 = Product.builder()
                .productId(0L)
                .name("볼펜")
                .price(10000L)
                .shippingCost(2500L)
                .description("예쁜 볼펜")
                .build();

        List<ProductOption> productOptionList = Arrays.asList(
                ProductOption.builder()
                        .productOptionId(0L)
                        .product(product1)
                        .type(OptionType.SELECT)
                        .optionValue("[빨강,파랑]")
                        .optionPrice(500L)
                        .build()
                , ProductOption.builder()
                        .productOptionId(1L)
                        .product(product1)
                        .type(OptionType.INPUT)
                        .optionValue("각인 : 김철수")
                        .optionPrice(500L)
                        .build()
        );

        when(productOptionService.getAllProductOptions(product1.getProductId())).thenReturn(productOptionList);

        // when
        MvcResult result = mockMvc.perform(get(UserProductControllerV1.PATH_NAME + String.format("/%d/options", product1.getProductId()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // then
        String response = result.getResponse().getContentAsString();
        assertThat(response).contains("빨강");
        assertThat(response).contains("각인");

    }

}
