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
    @Column(name = "TYPE", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private OptionType type;

    /**
     * VALUE : 옵션 값 목록
     */
    @Column(name = "VALUE", length = 1000)
    private String value;

    /**
     * OPTION_PRICE : 옵션 추가 금액
     */
    @Column(name = "OPTION_PRICE")
    private Long optionPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

}
