package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Store;
import com.example.demo.repository.StoreRepository;

@Service
public class StoreService {

	private final StoreRepository storeRepository;

	@Autowired
	public StoreService(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}

	public List<Store> getAllStores() {
		return storeRepository.findAll();
	}

	public Store saveStore(Store store) {
		return storeRepository.save(store);
	}

	public Store getStoreById(Long id) {
		return storeRepository.findById(id).orElse(null);
	}

	public void deleteStore(Long id) {
		storeRepository.deleteById(id);
	}
}
