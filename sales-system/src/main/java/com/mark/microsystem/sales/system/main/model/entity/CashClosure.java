package com.mark.microsystem.sales.system.main.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cash_closure")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CashClosure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cash_closure")
    private Integer id;

    @Column(name ="created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt;

    @Column(name ="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id_user_person"
    )
    private UserPerson user;

    @Column(name = "date")
    private LocalDateTime date;

    @Column(
            name = "daily_total",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal dailyTotal;

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
