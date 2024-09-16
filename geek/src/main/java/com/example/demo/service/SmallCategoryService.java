package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SmallCategory;
import com.example.demo.repository.SmallCategoryRepository;

@Service
public class SmallCategoryService {

	private final SmallCategoryRepository smallCategoryRepository;

	@Autowired
	public SmallCategoryService(SmallCategoryRepository smallCategoryRepository) {
		this.smallCategoryRepository = smallCategoryRepository;
	}

	public List<SmallCategory> findByMediumCategoryId(Long mediumCategoryId) {
		return smallCategoryRepository.findByMediumCategoryId(mediumCategoryId);
	}

	public SmallCategory findById(Long id) {
		return smallCategoryRepository.findById(id).orElse(null);
	}
}
