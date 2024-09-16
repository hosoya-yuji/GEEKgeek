package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductStoreDetail;
import com.example.demo.entity.Store;
import com.example.demo.repository.ProductStoreDetailRepository;

@Service
public class ProductStoreDetailService {

	@Autowired
	private ProductStoreDetailRepository productStoreDetailRepository;

	public void save(ProductStoreDetail detail) {
		productStoreDetailRepository.save(detail);
	}

	public Optional<ProductStoreDetail> findByProductAndStore(Long productId, Long storeId) {
		Product product = new Product();
		product.setId(productId);

		Store store = new Store();
		store.setId(storeId);

		return productStoreDetailRepository.findByProductAndStore(product, store);
	}

	public List<ProductStoreDetail> findByStoreId(Long storeId) {
		Store store = new Store();
		store.setId(storeId);

		return productStoreDetailRepository.findByStore(store);
	}

	public List<ProductStoreDetail> findByProductId(Long productId) {
		Product product = new Product();
		product.setId(productId);

		return productStoreDetailRepository.findByProduct(product);
	}
}
