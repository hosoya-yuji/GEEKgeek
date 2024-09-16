package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Store;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.StoreService;

@Service
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;

	@Autowired
	public StoreServiceImpl(StoreRepository storeRepository) {
		this.storeRepository = storeRepository;
	}

	@Override
	public List<Store> getAllStores() {
		return storeRepository.findAll();
	}

	@Override
	public Store findById(Long id) {
		return storeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Store not found"));
	}

	@Override
	public Store getStoreById(Long id) {
		return findById(id);
	}

	@Override
	public void saveStore(Store store) {
		storeRepository.save(store);
	}

	@Override
	public void deleteStore(Long id) {
		storeRepository.deleteById(id);
	}
}
