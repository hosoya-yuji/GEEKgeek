package com.example.demo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.OrderHistory;
import com.example.demo.entity.ProductStoreDetail;
import com.example.demo.repository.OrderHistoryRepository;
import com.example.demo.repository.ProductStoreDetailRepository;

@Service
public class OrderService {

	@Autowired
	private OrderHistoryRepository orderHistoryRepository;

	@Autowired
	private ProductStoreDetailRepository productStoreDetailRepository;

	@Autowired
	private AdminService adminService;

	public void placeOrder(Long productId, Long storeId, int orderQuantity, Admin admin) {
		if (orderQuantity <= 0) {
			throw new IllegalArgumentException("発注数量は1以上である必要があります。");
		}

		Optional<ProductStoreDetail> productStoreDetailOpt = productStoreDetailRepository
				.findById(new ProductStoreDetail.ProductStoreDetailId(productId, storeId));
		if (productStoreDetailOpt.isPresent()) {
			ProductStoreDetail productStoreDetail = productStoreDetailOpt.get();
			int updatedStock = productStoreDetail.getStockQuantity() + orderQuantity;

			productStoreDetail.setStockQuantity(updatedStock);
			productStoreDetailRepository.save(productStoreDetail);
		} else {
			throw new IllegalArgumentException("指定された商品と店舗の在庫が見つかりません。");
		}

		OrderHistory orderHistory = new OrderHistory();
		orderHistory.setProduct(productStoreDetailOpt.get().getProduct());
		orderHistory.setStore(productStoreDetailOpt.get().getStore());
		orderHistory.setOrderQuantity(orderQuantity);
		orderHistory.setTotalAmount(calculateTotalAmount(productId, storeId, orderQuantity));
		orderHistory.setAdmin(admin);
		orderHistoryRepository.save(orderHistory);
	}

	private BigDecimal calculateTotalAmount(Long productId, Long storeId, int orderQuantity) {
		Optional<ProductStoreDetail> productStoreDetailOpt = productStoreDetailRepository
				.findById(new ProductStoreDetail.ProductStoreDetailId(productId, storeId));
		return productStoreDetailOpt.map(
				productStoreDetail -> productStoreDetail.getSalePrice().multiply(BigDecimal.valueOf(orderQuantity)))
				.orElseThrow(() -> new IllegalArgumentException("商品または店舗情報が無効です。"));
	}

	public List<OrderHistory> getOrderHistoriesByStore(Long storeId) {
		return orderHistoryRepository.findByStoreId(storeId);
	}

	public List<OrderHistory> getAllOrderHistories() {
		return orderHistoryRepository.findAll();
	}
}
