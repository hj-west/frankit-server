package com.frankit.entity;

import com.frankit.entity.enums.OptionType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FRAN_PRODUCT_OPTION")
public class ProductOption extends BaseEntity {
    /**
     * PRODUCT_OPTION_ID : PRODUCT_OPTION_ID
     */
    @Id
    @Column(name = "PRODUCT_OPTION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productOptionId;

    /**
     * TYPE : 옵션 타입
     */
    @Column(nullable = false, columnDefinition = "VARCHAR(10) CHECK (type IN ('INPUT', 'SELECT'))")
    @Enumerated(EnumType.STRING)
    private OptionType type;

    /**
     * VALUE : 옵션 값 목록
     */
    @Column(name = "OPTION_VALUE", length = 1000)
    private String optionValue;

    /**
     * OPTION_PRICE : 옵션 추가 금액
     */
    @Column(name = "OPTION_PRICE")
    private Long optionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

}
