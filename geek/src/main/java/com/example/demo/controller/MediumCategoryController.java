package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.MediumCategory;
import com.example.demo.service.MediumCategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/mediumCategory")
public class MediumCategoryController {

	private final MediumCategoryService mediumCategoryService;

	@Autowired
	public MediumCategoryController(MediumCategoryService mediumCategoryService) {
		this.mediumCategoryService = mediumCategoryService;
	}

	@GetMapping("/view/{id}")
	public String viewMediumCategory(@PathVariable Long id, Model model, HttpServletRequest request,
			HttpSession session) {
		MediumCategory mediumCategory = mediumCategoryService.findByIdWithSmallCategories(id);


		session.removeAttribute("backUrl");


		String backUrl = "/admin/largeCategory/view/" + mediumCategory.getLargeCategory().getId();
		session.setAttribute("backUrl", backUrl);

		model.addAttribute("mediumCategory", mediumCategory);
		model.addAttribute("smallCategories", mediumCategory.getSmallCategories());
		model.addAttribute("backUrl", backUrl);

		return "mediumCategory/view";
	}
}
