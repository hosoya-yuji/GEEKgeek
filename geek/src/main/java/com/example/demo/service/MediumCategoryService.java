package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.MediumCategory;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.MediumCategoryRepository;

@Service
public class MediumCategoryService {

	private final MediumCategoryRepository mediumCategoryRepository;

	@Autowired
	public MediumCategoryService(MediumCategoryRepository mediumCategoryRepository) {
		this.mediumCategoryRepository = mediumCategoryRepository;
	}

	public Optional<MediumCategory> findById(Long id) {
		return mediumCategoryRepository.findById(id);
	}

	public List<MediumCategory> findByLargeCategoryId(Long largeCategoryId) {
		return mediumCategoryRepository.findByLargeCategoryId(largeCategoryId);
	}

	@Transactional
	public MediumCategory findByIdWithSmallCategories(Long id) {
		return mediumCategoryRepository.findByIdWithSmallCategories(id)
				.orElseThrow(() -> new ResourceNotFoundException("Medium Category not found"));
	}
}
