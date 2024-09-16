package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;

@Controller
@RequestMapping("/admin/profile")
public class AdminProfileController {

	private final AdminService adminService;

	@Autowired
	public AdminProfileController(AdminService adminService) {
		this.adminService = adminService;
	}

	private String getCurrentAdminEmail() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
			return ((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername();
		} else {
			throw new RuntimeException("現在の管理者情報が取得できませんでした。");
		}
	}

	@GetMapping
	public String showProfile(Model model) {
		String currentEmail = getCurrentAdminEmail();
		Admin admin = adminService.findByEmail(currentEmail);

		if (admin != null) { // Admin が null でないか確認
			model.addAttribute("admin", admin);
		} else {
			throw new RuntimeException("Admin not found");
		}

		return "adminprofile/userprofile";
	}

	@GetMapping("/edit")
	public String editProfile(Model model) {
		String currentEmail = getCurrentAdminEmail();
		Admin admin = adminService.findByEmail(currentEmail);

		if (admin != null) { // Admin が null でないか確認
			model.addAttribute("admin", admin);
		} else {
			throw new RuntimeException("Admin not found");
		}

		return "adminprofile/editprofile";
	}

	@PostMapping("/edit")
	public String updateProfile(@ModelAttribute("admin") Admin admin, Model model) {
		adminService.updateAdminProfile(admin);
		return "redirect:/admin/profile";
	}
}
