package com.mark.microsystem.sales.system.main.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoice")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_invoice")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sale_id",
            referencedColumnName = "id_sale"
    )
    private Sale sale;

    @Column(name = "customer_rfc", length = 13)
    private String customerRfc;

    @Column(name = "business_name", length = 150)
    private String businessName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "date")
    private LocalDateTime date;

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
