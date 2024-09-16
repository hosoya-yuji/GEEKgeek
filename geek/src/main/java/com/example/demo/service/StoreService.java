package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Store;

public interface StoreService {
	List<Store> getAllStores();

	Store findById(Long id);

	Store getStoreById(Long id);

	void saveStore(Store store);

	void deleteStore(Long id);
}
