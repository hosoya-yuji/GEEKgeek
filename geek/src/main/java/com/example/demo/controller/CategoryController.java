package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.MediumCategory;
import com.example.demo.entity.SmallCategory;
import com.example.demo.service.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping("/medium-categories/{largeCategoryId}")
	@ResponseBody
	public List<MediumCategory> getMediumCategories(@PathVariable Long largeCategoryId) {
		return categoryService.getMediumCategoriesByLargeCategory(largeCategoryId);
	}

	@GetMapping("/small-categories/{mediumCategoryId}")
	@ResponseBody
	public List<SmallCategory> getSmallCategories(@PathVariable Long mediumCategoryId) {
		return categoryService.getSmallCategoriesByMediumCategory(mediumCategoryId);
	}
}
