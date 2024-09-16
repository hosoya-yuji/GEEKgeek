package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LargeCategory;
import com.example.demo.repository.LargeCategoryRepository;

@Service
public class LargeCategoryService {

	private final LargeCategoryRepository largeCategoryRepository;

	@Autowired
	public LargeCategoryService(LargeCategoryRepository largeCategoryRepository) {
		this.largeCategoryRepository = largeCategoryRepository;
	}

	public List<LargeCategory> findAll() {
		return largeCategoryRepository.findAll();
	}

	public Optional<LargeCategory> findById(Long id) {
		return largeCategoryRepository.findById(id);
	}
}
