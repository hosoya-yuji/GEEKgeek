package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Product;
import com.example.demo.service.AdminService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.StoreService;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;
	private final ManufacturerService manufacturerService;
	private final StoreService storeService;
	private final OrderService orderService;
	private final AdminService adminService;

	@Autowired
	public ProductController(ProductService productService, CategoryService categoryService,
			ManufacturerService manufacturerService,
			StoreService storeService, OrderService orderService, AdminService adminService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.manufacturerService = manufacturerService;
		this.storeService = storeService;
		this.orderService = orderService;
		this.adminService = adminService;
	}

	@GetMapping
	public String listProducts(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") Long largeCategoryId,
			@RequestParam(defaultValue = "0") Long mediumCategoryId,
			@RequestParam(defaultValue = "0") Long smallCategoryId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			Model model) {

		Pageable pageable = PageRequest.of(page, size);

		if (largeCategoryId == 0)
			largeCategoryId = null;
		if (mediumCategoryId == 0)
			mediumCategoryId = null;
		if (smallCategoryId == 0)
			smallCategoryId = null;

		Page<Product> productPage = productService.searchProducts(name, largeCategoryId, mediumCategoryId,
				smallCategoryId, pageable);

		model.addAttribute("largeCategories", categoryService.getAllLargeCategories());
		model.addAttribute("mediumCategories", categoryService.getMediumCategoriesByLargeCategory(largeCategoryId));
		model.addAttribute("smallCategories", categoryService.getSmallCategoriesByMediumCategory(mediumCategoryId));

		model.addAttribute("products", productPage.getContent());
		model.addAttribute("page", productPage);
		model.addAttribute("name", name);
		model.addAttribute("largeCategoryId", largeCategoryId);
		model.addAttribute("mediumCategoryId", mediumCategoryId);
		model.addAttribute("smallCategoryId", smallCategoryId);

		return "products/list";
	}

	@GetMapping("/new")
	public String createProductForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
		return "products/new";
	}

	@PostMapping
	public String saveProduct(@ModelAttribute Product product) {
		productService.saveProduct(product);
		return "redirect:/admin/products";
	}

	@GetMapping("/{id}")
	public String viewProduct(@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		return "products/view";
	}

	@GetMapping("/edit/{id}")
	public String editProductForm(@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());
		return "products/edit";
	}

	@PostMapping("/edit/{id}")
	public String updateProduct(@PathVariable Long id, @ModelAttribute Product product) {
		product.setId(id);
		productService.saveProduct(product);
		return "redirect:/admin/products";
	}

	@PostMapping("/delete/{id}")
	public String deleteProduct(@PathVariable Long id) {
		productService.deleteProduct(id);
		return "redirect:/admin/products";
	}

	@GetMapping("/{id}/order")
	public String showOrderPage(@PathVariable Long id, Model model) {
		Product product = productService.findById(id);
		model.addAttribute("product", product);
		return "orders/order";
	}

	@PostMapping("/order")
	public String orderProduct(@RequestParam Long productId, @RequestParam Integer quantity,
			@AuthenticationPrincipal UserDetails userDetails) {
		Admin admin = adminService.findByEmail(userDetails.getUsername());
		Long storeId = admin.getStore().getId();

		orderService.placeOrder(productId, storeId, quantity, admin);

		return "redirect:/admin/products";
	}
}
