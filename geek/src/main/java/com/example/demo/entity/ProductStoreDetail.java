package com.example.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "product_store_details")
@Data
public class ProductStoreDetail {

	@EmbeddedId
	private ProductStoreDetailId id;

	@Column(name = "sale_price", precision = 10, scale = 2)
	private BigDecimal salePrice;

	@Column(name = "stock_quantity")
	private Integer stockQuantity;

	@Column(name = "created_at")
	private Timestamp createdAt;

	@Column(name = "updated_at")
	private Timestamp updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", insertable = false, updatable = false, referencedColumnName = "product_id")
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "store_id", insertable = false, updatable = false)
	private Store store;

	// Stock getter and setter
	public Integer getStock() {
		return stockQuantity;
	}

	public void setStock(Integer stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	@Embeddable
	@Data
	public static class ProductStoreDetailId implements java.io.Serializable {
		@Column(name = "product_id")
		private Long productId;

		@Column(name = "store_id")
		private Long storeId;

		public ProductStoreDetailId(Long productId, Long storeId) {
			this.productId = productId;
			this.storeId = storeId;
		}

		public ProductStoreDetailId() {
		}

		@Override
		public int hashCode() {
			return Objects.hash(productId, storeId);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			ProductStoreDetailId that = (ProductStoreDetailId) obj;
			return Objects.equals(productId, that.productId) && Objects.equals(storeId, that.storeId);
		}
	}
}
