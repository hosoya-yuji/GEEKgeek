package com.example.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_store_details")
@Data
@NoArgsConstructor
public class ProductStoreDetail {

    @EmbeddedId
    private ProductStoreDetailId id;

    @Column(name = "sale_price", precision = 10, scale = 2)
    private BigDecimal salePrice;

    @Column(name = "stock_quantity")
    private Integer stockQuantity;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", insertable = false, updatable = false, referencedColumnName = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "store_id", insertable = false, updatable = false)
    private Store store;

    // 自動的に作成日時と更新日時を設定
    @PrePersist
    protected void onCreate() {
        this.createdAt = Timestamp.valueOf(LocalDateTime.now());
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Timestamp.valueOf(LocalDateTime.now());
    }

    @Embeddable
    @Data
    @NoArgsConstructor
    public static class ProductStoreDetailId implements java.io.Serializable {
        @Column(name = "product_id")
        private Long productId;

        @Column(name = "store_id")
        private Long storeId;

        public ProductStoreDetailId(Long productId, Long storeId) {
            this.productId = productId;
            this.storeId = storeId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ProductStoreDetailId that = (ProductStoreDetailId) o;
            return Objects.equals(productId, that.productId) &&
                    Objects.equals(storeId, that.storeId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(productId, storeId);
        }
    }
}
