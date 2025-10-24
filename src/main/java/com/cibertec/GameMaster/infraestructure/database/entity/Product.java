package com.cibertec.GameMaster.infraestructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_products")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 1000)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Lob
    private String details;

    @Column(nullable = false)
    private BigDecimal price;

    @Column
    private int stock;

    @Column(length = 1000)
    private String imgUrl;

    @Builder.Default
    @Column(columnDefinition = "BIT(1) DEFAULT 1")
    private Boolean status = true;

}