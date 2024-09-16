package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.Admin;
import com.example.demo.entity.OrderHistory;
import com.example.demo.service.AdminService;
import com.example.demo.service.OrderService;

@Controller
@RequestMapping("/admin/orders")
public class OrderController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AdminService adminService;


	@GetMapping
	public String showOrderHistory(@AuthenticationPrincipal UserDetails userDetails, Model model) {

		Admin admin = adminService.findByEmail(userDetails.getUsername());


		List<OrderHistory> orderHistories = orderService.getOrderHistoriesByStore(admin.getStore().getId());


		model.addAttribute("orderHistories", orderHistories);

		return "orders/orderHistory";
	}
}
