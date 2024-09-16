package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Store;
import com.example.demo.service.StoreService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin/stores")
public class StoreController {

	private final StoreService storeService;

	@Autowired
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}

	@GetMapping
	public String getAllStores(Model model) {
		List<Store> stores = storeService.getAllStores();
		model.addAttribute("stores", stores);
		return "store/list";
	}

	@GetMapping("/new")
	public String showNewStoreForm(Model model) {
		model.addAttribute("store", new Store());
		return "store/new";
	}

	@PostMapping("/new")
	public String saveStore(@Valid @ModelAttribute("store") Store store, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        return "store/new";
	    }
	    storeService.saveStore(store);
	    return "redirect:/admin/stores";
	}

	@GetMapping("/edit/{id}")
	public String showEditStoreForm(@PathVariable("id") Long id, Model model) {
		Store store = storeService.getStoreById(id);
		if (store == null) {
			return "redirect:/admin/stores?error=notfound";
		}
		model.addAttribute("store", store);
		return "store/edit";
	}

	@PostMapping("/update/{id}")
	public String updateStore(@PathVariable("id") Long id, @Valid @ModelAttribute("store") Store store,
			BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "store/edit";
		}

		Store existingStore = storeService.getStoreById(id);
		if (existingStore == null) {
			return "redirect:/admin/stores?error=notfound";
		}
		existingStore.setStoreName(store.getStoreName());
		existingStore.setAddress(store.getAddress());
		storeService.saveStore(existingStore);
		return "redirect:/admin/stores";
	}

	@GetMapping("/delete/{id}")
	public String deleteStore(@PathVariable("id") Long id) {
	    storeService.deleteStore(id);
	    return "redirect:/admin/stores";
	}
}
