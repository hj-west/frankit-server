package com.frankit.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "FRAN_PRODUCT")
public class Product extends BaseEntity {
    /**
     * PRODUCT_ID : PRODUCT_ID
     */
    @Id
    @Column(name = "PRODUCT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    /**
     * NAME : 상품명
     */
    @Column(name = "NAME", nullable = false, length = 100)
    private String name;

    /**
     * DESCRIPTION : 상품 설명
     */
    @Column(name = "DESCRIPTION", length = 1000)
    private String description;

    /**
     * PRICE : 상품 가격
     */
    @Column(name = "DESCRIPTION")
    private Long price;

    /**
     * SHIPPING_COST : 배송비
     */
    @Column(name = "SHIPPING_COST")
    private Long shippingCost;
}
