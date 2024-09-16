package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.LargeCategory;
import com.example.demo.entity.Manufacturer;
import com.example.demo.entity.MediumCategory;
import com.example.demo.entity.SmallCategory;
import com.example.demo.repository.LargeCategoryRepository;
import com.example.demo.repository.ManufacturerRepository;
import com.example.demo.repository.MediumCategoryRepository;
import com.example.demo.repository.SmallCategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private LargeCategoryRepository largeCategoryRepository;

	@Autowired
	private MediumCategoryRepository mediumCategoryRepository;

	@Autowired
	private SmallCategoryRepository smallCategoryRepository;

	@Autowired
	private ManufacturerRepository manufacturerRepository;

	public List<LargeCategory> getAllLargeCategories() {
		return largeCategoryRepository.findAll();
	}

	public List<MediumCategory> getMediumCategoriesByLargeCategory(Long largeCategoryId) {
		return mediumCategoryRepository.findByLargeCategoryId(largeCategoryId);
	}

	public List<SmallCategory> getSmallCategoriesByMediumCategory(Long mediumCategoryId) {
		return smallCategoryRepository.findByMediumCategoryId(mediumCategoryId);
	}

	public List<Manufacturer> getAllManufacturers() {
		return manufacturerRepository.findAll();
	}
}
