package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Store;
import com.example.demo.service.AdminService;
import com.example.demo.service.StoreService;

@Controller
public class AdminController {

	@Autowired
	private AdminService adminService;

	@Autowired
	private StoreService storeService;

	@GetMapping("/admin/signin")
	public String showSignInForm() {
		return "signin";
	}

	@GetMapping("/admin/signup")
	public String showSignUpForm(Model model) {
		model.addAttribute("admin", new Admin());

		model.addAttribute("stores", storeService.getAllStores());
	return "signup";
	}

	@PostMapping("/admin/signup")
	public String registerAdmin(@ModelAttribute("admin") Admin admin, Model model) {
		try {

			Store selectedStore = storeService.getStoreById(admin.getStore().getId());
			admin.setStore(selectedStore);

			adminService.saveAdmin(admin);
		} catch (IllegalStateException e) {
			model.addAttribute("error", e.getMessage());

			model.addAttribute("stores", storeService.getAllStores());
			return "signup";
		}
		return "redirect:/admin/signin";
	}

	@GetMapping("/admin/dashboard")
	public String showDashboard(Model model) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String email = auth.getName();
		model.addAttribute("email", email);
	return "dashboard";
	}

	@GetMapping("/admin/management/edit/{id}")
	public String showEditAdminForm(@PathVariable("id") Long id, Model model) {
		Admin admin = adminService.findById(id);
		model.addAttribute("admin", admin);

		model.addAttribute("stores", storeService.getAllStores());
		return "management/edit"; 
	}

	@PostMapping("/admin/management/edit/{id}")
	public String updateAdmin(@PathVariable("id") Long id, @ModelAttribute("admin") Admin admin, Model model) {
		try {

			Store selectedStore = storeService.getStoreById(admin.getStore().getId());
			admin.setStore(selectedStore);

			adminService.saveAdmin(admin);
		} catch (Exception e) {
			model.addAttribute("error", "更新中にエラーが発生しました");
			model.addAttribute("stores", storeService.getAllStores());
			return "management/edit";
		}
		return "redirect:/admin/management";
	}
}
