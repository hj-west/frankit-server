package com.frankit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    @Column(name = "PRICE", nullable = false)
    private Long price;

    /**
     * SHIPPING_COST : 배송비
     */
    @Column(name = "SHIPPING_COST")
    private Long shippingCost;


    // cascade : 부모의 변경이 자식에게도 영향을 미침, orphanRemoval : 부모가 삭제되면 자식도 삭제
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ProductOption> options;
}
