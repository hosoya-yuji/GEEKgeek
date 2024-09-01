package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Admin;
import com.example.demo.service.AdminService;
import com.example.demo.service.StoreService;

@Controller
@RequestMapping("/admin/management")
public class AdminManagementController {

	private final AdminService adminService;
	private final StoreService storeService;

	@Autowired
	public AdminManagementController(AdminService adminService, StoreService storeService) {
		this.adminService = adminService;
		this.storeService = storeService;
	}

	@GetMapping
	public String listAdmins(Model model) {
		List<Admin> admins = adminService.findAll();
		model.addAttribute("admins", admins);
		return "management/list";
	}

	@GetMapping("/view/{id}")
	public String viewAdmin(@PathVariable Long id, Model model) {
		Admin admin = adminService.findById(id);
		if (admin == null) {
			System.out.println("Admin with ID " + id + " not found.");
			throw new IllegalArgumentException("Invalid admin Id:" + id);
		}
		System.out.println("Admin with ID " + id + " found.");
		model.addAttribute("admin", admin);
		return "management/view";
	}

	@GetMapping("/new")
	public String createAdminForm(Model model) {
		model.addAttribute("admin", new Admin());
		model.addAttribute("stores", storeService.getAllStores());
		return "management/new";
	}

	@PostMapping("/new")
	public String saveAdmin(@ModelAttribute("admin") Admin admin) {
		adminService.saveAdmin(admin);
		return "redirect:/admin/management";
	}

	@GetMapping("/edit-admin/{id}")
	public String editAdminForm(@PathVariable Long id, Model model) {
		Admin admin = adminService.findById(id);
		model.addAttribute("admin", admin);
		model.addAttribute("stores", storeService.getAllStores());
		return "management/edit";
	}

	@PostMapping("/edit-admin/{id}")
	public String updateAdmin(@PathVariable Long id, @ModelAttribute("admin") Admin admin, Model model) {
		try {
			Admin existingAdmin = adminService.findById(id);
			if (existingAdmin != null) {
				// 必要なフィールドを更新
				existingAdmin.setFirstName(admin.getFirstName());
				existingAdmin.setLastName(admin.getLastName());
				existingAdmin.setEmail(admin.getEmail());
				// 店舗の紐付けと保存をサービス層で行う
				existingAdmin.setStore(admin.getStore());

				adminService.saveAdmin(existingAdmin);
			}
		} catch (Exception e) {
			model.addAttribute("error", "更新中にエラーが発生しました");
			model.addAttribute("stores", storeService.getAllStores());
			return "management/edit";
		}
		return "redirect:/admin/management";
	}

	@GetMapping("/delete/{id}")
	public String deleteAdmin(@PathVariable Long id) {
		adminService.deleteById(id);
		return "redirect:/admin/management";
	}
}
