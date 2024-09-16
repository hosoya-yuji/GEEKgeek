package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.SmallCategory;
import com.example.demo.service.SmallCategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/smallCategory")
public class SmallCategoryController {

	private final SmallCategoryService smallCategoryService;

	@Autowired
	public SmallCategoryController(SmallCategoryService smallCategoryService) {
		this.smallCategoryService = smallCategoryService;
	}

	@GetMapping("/view/{id}")
	public String viewSmallCategory(@PathVariable Long id, Model model, HttpServletRequest request,
			HttpSession session) {
		SmallCategory smallCategory = smallCategoryService.findById(id);
		if (smallCategory == null) {
			throw new IllegalArgumentException("Invalid small category Id:" + id);
		}


		String backUrl = (String) session.getAttribute("backUrl");
		if (backUrl == null) {

			backUrl = request.getHeader("Referer");
			session.setAttribute("backUrl", backUrl);
		}

		model.addAttribute("smallCategory", smallCategory);
		model.addAttribute("backUrl", backUrl);

		return "smallCategory/view";
	}
}
