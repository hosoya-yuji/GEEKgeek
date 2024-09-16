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

import com.example.demo.entity.Manufacturer;
import com.example.demo.service.ManufacturerService;

@Controller
@RequestMapping("/admin/manufacturer")
public class ManufacturerController {

	private final ManufacturerService manufacturerService;

	@Autowired
	public ManufacturerController(ManufacturerService manufacturerService) {
		this.manufacturerService = manufacturerService;
	}

	@GetMapping
	public String listManufacturers(Model model) {
		List<Manufacturer> manufacturers = manufacturerService.findAll();
		model.addAttribute("manufacturers", manufacturers);
		return "manufacturer/list";
	}

	@GetMapping("/view/{id}")
	public String viewManufacturer(@PathVariable Long id, Model model) {
		Manufacturer manufacturer = manufacturerService.findById(id);
		model.addAttribute("manufacturer", manufacturer);
		return "manufacturer/view";
	}

	@GetMapping("/new")
	public String createManufacturerForm(Model model) {
		model.addAttribute("manufacturer", new Manufacturer());
		return "manufacturer/new";
	}

	@PostMapping("/new")
	public String saveManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer) {
		manufacturerService.save(manufacturer);
		return "redirect:/admin/manufacturer";
	}

	@GetMapping("/edit/{id}")
	public String editManufacturerForm(@PathVariable Long id, Model model) {
		Manufacturer manufacturer = manufacturerService.findById(id);
		model.addAttribute("manufacturer", manufacturer);
		return "manufacturer/edit";
	}

	@PostMapping("/edit/{id}")
	public String updateManufacturer(@PathVariable Long id, @ModelAttribute("manufacturer") Manufacturer manufacturer) {
		manufacturerService.save(manufacturer);
		return "redirect:/admin/manufacturer";
	}

	@GetMapping("/delete/{id}")
	public String deleteManufacturer(@PathVariable Long id) {
		manufacturerService.deleteById(id);
		return "redirect:/admin/manufacturer";
	}
}
