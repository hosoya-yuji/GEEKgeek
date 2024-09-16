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
import com.example.demo.service.PositionService;
import com.example.demo.service.RoleService;
import com.example.demo.service.StoreService;

@Controller
@RequestMapping("/admin/management")
public class AdminManagementController {

	private final AdminService adminService;
	private final StoreService storeService;
	private final RoleService roleService;
	private final PositionService positionService;

	@Autowired
	public AdminManagementController(AdminService adminService, StoreService storeService,
			RoleService roleService, PositionService positionService) {
		this.adminService = adminService;
		this.storeService = storeService;
		this.roleService = roleService;
		this.positionService = positionService;
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
		model.addAttribute("admin", admin);
		return "management/view";
	}

	@GetMapping("/new")
	public String createAdminForm(Model model) {
		model.addAttribute("admin", new Admin());
		model.addAttribute("stores", storeService.getAllStores());
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("positions", positionService.findAll());
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
		model.addAttribute("roles", roleService.findAll());
		model.addAttribute("positions", positionService.findAll());

		return "management/edit";
	}

	@PostMapping("/edit-admin/{id}")
	public String updateAdmin(@PathVariable Long id, @ModelAttribute("admin") Admin admin, Model model) {
		try {
			Admin existingAdmin = adminService.findById(id);
			if (existingAdmin != null) {
				existingAdmin.setFirstName(admin.getFirstName());
				existingAdmin.setLastName(admin.getLastName());
				existingAdmin.setEmail(admin.getEmail());
				existingAdmin.setStore(admin.getStore());

				existingAdmin.setRole(admin.getRole());
				existingAdmin.setPosition(admin.getPosition());

				adminService.saveAdmin(existingAdmin);
			}
		} catch (Exception e) {
			model.addAttribute("error", "更新中にエラーが発生しました");
			model.addAttribute("stores", storeService.getAllStores());
			model.addAttribute("roles", roleService.findAll());
			model.addAttribute("positions", positionService.findAll());
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
