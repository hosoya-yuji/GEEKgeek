package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductStoreDetail;
import com.example.demo.entity.Store;
import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.ProductStoreDetailRepository;
import com.example.demo.repository.StoreRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductStoreDetailRepository productStoreDetailsRepository;
	private final StoreRepository storeRepository;

	@Autowired
	public ProductService(ProductRepository productRepository,
			ProductStoreDetailRepository productStoreDetailsRepository, StoreRepository storeRepository) {
		this.productRepository = productRepository;
		this.productStoreDetailsRepository = productStoreDetailsRepository;
		this.storeRepository = storeRepository;
	}


	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Page<Product> searchProducts(String name, Long largeCategoryId, Long mediumCategoryId, Long smallCategoryId,
			Pageable pageable) {
		return productRepository.findByProductNameContainingAndLargeCategoryIdAndMediumCategoryIdAndSmallCategoryId(
				name, largeCategoryId, mediumCategoryId, smallCategoryId, pageable);
	}

	public Product findById(Long id) {
		return productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundException("Product with ID " + id + " not found"));
	}

	public void saveProduct(Product product) {
		productRepository.save(product);
	}

	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

	@Transactional
	public void orderProduct(Long productId, Integer quantity, Long storeId) {
		Product product = findById(productId);
		Store store = storeRepository.findById(storeId)
				.orElseThrow(() -> new RuntimeException("Store with ID " + storeId + " not found"));
		ProductStoreDetail detail = productStoreDetailsRepository.findByProductAndStore(product, store)
				.orElseThrow(() -> new RuntimeException(
						"Store details not found for product ID " + productId + " and store ID " + storeId));
		detail.setStock(detail.getStock() + quantity);
		productStoreDetailsRepository.save(detail);
	}
}
