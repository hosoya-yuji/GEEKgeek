package com.example.demo.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "large_category_id")
	private LargeCategory largeCategory;

	@ManyToOne
	@JoinColumn(name = "medium_category_id")
	private MediumCategory mediumCategory;

	@ManyToOne
	@JoinColumn(name = "small_category_id")
	private SmallCategory smallCategory;

	@ManyToOne
	@JoinColumn(name = "manufacturer_id")
	private Manufacturer manufacturer;

	@Column(name = "product_name", length = 255)
	private String productName;

	@Column(name = "description", columnDefinition = "MEDIUMTEXT")
	private String description;

	@Column(name = "image_url", length = 255)
	private String imageUrl;

	@Column(name = "purchase_price", precision = 10, scale = 2)
	private BigDecimal purchasePrice;

	@Column(name = "suggested_retail_price", precision = 10, scale = 2)
	private BigDecimal suggestedRetailPrice;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductStoreDetail> productStoreDetails;

	@Column(name = "created_at", nullable = false, updatable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Timestamp createdAt;

	@Column(name = "updated_at", nullable = false, insertable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
	private Timestamp updatedAt;
}
