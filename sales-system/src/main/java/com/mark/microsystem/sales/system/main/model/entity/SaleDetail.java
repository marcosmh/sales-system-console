package com.mark.microsystem.sales.system.main.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sale_detail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sale_detail")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sale_id",
            referencedColumnName = "id_sale"
    )
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "product_id",
            referencedColumnName = "id_product"
    )
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(
            name = "subtotal",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal subtotal;

    @Column(name ="created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name ="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();

    }

}
