package com.example.demo.controller;

import java.beans.PropertyEditorSupport;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Product;
import com.example.demo.entity.ProductStoreDetail;
import com.example.demo.service.AdminService;
import com.example.demo.service.CategoryService;
import com.example.demo.service.ManufacturerService;
import com.example.demo.service.OrderService;
import com.example.demo.service.ProductService;
import com.example.demo.service.ProductStoreDetailService;
import com.example.demo.service.StoreService;

import jakarta.transaction.Transactional;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

	private final ProductService productService;
	private final CategoryService categoryService;
	private final ManufacturerService manufacturerService;
	private final StoreService storeService;
	private final OrderService orderService;
	private final AdminService adminService;
	private final ProductStoreDetailService productStoreDetailService;

	@Autowired
	public ProductController(ProductService productService, CategoryService categoryService,
			ManufacturerService manufacturerService,
			StoreService storeService, OrderService orderService, AdminService adminService,
			ProductStoreDetailService productStoreDetailService) {
		this.productService = productService;
		this.categoryService = categoryService;
		this.manufacturerService = manufacturerService;
		this.storeService = storeService;
		this.orderService = orderService;
		this.adminService = adminService;
		this.productStoreDetailService = productStoreDetailService;
	}

	@GetMapping
	public String listProducts(
			@RequestParam(defaultValue = "") String name,
			@RequestParam(defaultValue = "0") Long largeCategoryId,
			@RequestParam(defaultValue = "0") Long mediumCategoryId,
			@RequestParam(defaultValue = "0") Long smallCategoryId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@AuthenticationPrincipal UserDetails userDetails,
			Model model) {

		Pageable pageable = PageRequest.of(page, size);

		Admin admin = adminService.findByEmail(userDetails.getUsername());
		Long storeId = admin.getStore().getId();

		if (largeCategoryId == 0)
			largeCategoryId = null;
		if (mediumCategoryId == 0)
			mediumCategoryId = null;
		if (smallCategoryId == 0)
			smallCategoryId = null;

		Page<Product> productPage = productService.searchProducts(name, largeCategoryId, mediumCategoryId,
				smallCategoryId, pageable);

		List<ProductStoreDetail> productStoreDetails = productStoreDetailService.findByStoreId(storeId);
		Map<Long, ProductStoreDetail> productDetailMap = productStoreDetails.stream()
				.collect(Collectors.toMap(detail -> detail.getId().getProductId(), detail -> detail));

		model.addAttribute("largeCategories", categoryService.getAllLargeCategories());
		model.addAttribute("mediumCategories", categoryService.getMediumCategoriesByLargeCategory(largeCategoryId));
		model.addAttribute("smallCategories", categoryService.getSmallCategoriesByMediumCategory(mediumCategoryId));
		model.addAttribute("products", productPage.getContent());
		model.addAttribute("productDetailMap", productDetailMap);
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
	public String viewProduct(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails, Model model) {
		Product product = productService.findById(id);
		Admin admin = adminService.findByEmail(userDetails.getUsername());
		Long storeId = admin.getStore().getId();


		Optional<ProductStoreDetail> productStoreDetail = productStoreDetailService.findByProductAndStore(id, storeId);

		model.addAttribute("product", product);

		model.addAttribute("storeDetail", productStoreDetail.orElse(null));

		return "products/view";
	}

	@Transactional
	@GetMapping("/edit/{id}")
	public String editProductForm(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails,
			Model model) {
		Product product = productService.findById(id);

		Admin admin = adminService.findByEmail(userDetails.getUsername());
		Long storeId = admin.getStore().getId();

		Optional<ProductStoreDetail> productStoreDetail = productStoreDetailService.findByProductAndStore(id, storeId);

		model.addAttribute("productStoreDetail",
				productStoreDetail.orElseGet(() -> {
					ProductStoreDetail newDetail = new ProductStoreDetail();

					newDetail.setId(new ProductStoreDetail.ProductStoreDetailId(id, storeId));
					return newDetail;
				}));

		model.addAttribute("product", product);
		model.addAttribute("manufacturers", manufacturerService.getAllManufacturers());

		return "products/edit";
	}

	@PostMapping("/edit/{id}")
	public String updateProduct(@PathVariable Long id,
			@ModelAttribute("productStoreDetail") ProductStoreDetail productStoreDetail,
			BindingResult storeDetailResult,
			@AuthenticationPrincipal UserDetails userDetails) {


		if (storeDetailResult.hasErrors()) {
			System.out.println("StoreDetail binding errors: " + storeDetailResult.getAllErrors());
			return "products/edit";
		}

		Admin admin = adminService.findByEmail(userDetails.getUsername());
		Long storeId = admin.getStore().getId();

		ProductStoreDetail.ProductStoreDetailId detailId = new ProductStoreDetail.ProductStoreDetailId(id, storeId);
		productStoreDetail.setId(detailId);

		productStoreDetailService.save(productStoreDetail);

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

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(ProductStoreDetail.ProductStoreDetailId.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				String[] ids = text.split(",");
				if (ids.length == 2) {
					Long productId = Long.parseLong(ids[0].trim());
					Long storeId = Long.parseLong(ids[1].trim());
					setValue(new ProductStoreDetail.ProductStoreDetailId(productId, storeId));
				}
			}
		});
	}

}
