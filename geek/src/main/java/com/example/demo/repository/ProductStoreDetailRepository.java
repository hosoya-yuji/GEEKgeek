package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Product;
import com.example.demo.entity.ProductStoreDetail;
import com.example.demo.entity.ProductStoreDetail.ProductStoreDetailId;
import com.example.demo.entity.Store;

public interface ProductStoreDetailRepository extends JpaRepository<ProductStoreDetail, ProductStoreDetailId> {
	Optional<ProductStoreDetail> findByProductAndStore(Product product, Store store);
}
