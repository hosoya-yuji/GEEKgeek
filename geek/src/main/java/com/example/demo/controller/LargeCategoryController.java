package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.LargeCategory;
import com.example.demo.entity.MediumCategory;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.LargeCategoryService;
import com.example.demo.service.MediumCategoryService;

@Controller
@RequestMapping("/admin/largeCategory")
public class LargeCategoryController {

	private final LargeCategoryService largeCategoryService;
	private final MediumCategoryService mediumCategoryService;

	@Autowired
	public LargeCategoryController(LargeCategoryService largeCategoryService,
			MediumCategoryService mediumCategoryService) {
		this.largeCategoryService = largeCategoryService;
		this.mediumCategoryService = mediumCategoryService;
	}

	@GetMapping
	public String listLargeCategories(Model model) {
		List<LargeCategory> largeCategories = largeCategoryService.findAll();
		model.addAttribute("largeCategories", largeCategories);
		return "largeCategory/list";
	}

	@GetMapping("/view/{id}")
	public String viewLargeCategory(@PathVariable Long id, Model model) {
		LargeCategory largeCategory = largeCategoryService.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Large Category not found"));
		List<MediumCategory> mediumCategories = mediumCategoryService.findByLargeCategoryId(id);
		model.addAttribute("largeCategory", largeCategory);
		model.addAttribute("mediumCategories", mediumCategories);
		return "largeCategory/view";
	}

}
