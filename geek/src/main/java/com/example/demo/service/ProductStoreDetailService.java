package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.ProductStoreDetail;
import com.example.demo.repository.ProductStoreDetailRepository;

@Service
public class ProductStoreDetailService {

	@Autowired
	private ProductStoreDetailRepository productStoreDetailRepository;

	public void save(ProductStoreDetail detail) {
		productStoreDetailRepository.save(detail);
	}
}
