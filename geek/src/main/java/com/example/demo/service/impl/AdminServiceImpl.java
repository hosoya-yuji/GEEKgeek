package com.example.demo.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Admin;
import com.example.demo.entity.Store;
import com.example.demo.repository.AdminRepository;
import com.example.demo.repository.StoreRepository;
import com.example.demo.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

	private final AdminRepository adminRepository;
	private final StoreRepository storeRepository;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public AdminServiceImpl(AdminRepository adminRepository, StoreRepository storeRepository,
			PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.storeRepository = storeRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public List<Admin> findAll() {
		return adminRepository.findAll();
	}

	@Override
	public Admin findById(Long id) {
		return adminRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Admin not found"));
	}

	@Override
	public Admin save(Admin admin) {
		admin.setPassword(passwordEncoder.encode(admin.getPassword()));

		return adminRepository.save(admin);
	}

	@Override
	public void deleteById(Long id) {
		adminRepository.deleteById(id);
	}

	@Override
	public Admin findByEmail(String email) {
		return adminRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Admin not found"));
	}

	@Override
	public Admin updateAdminProfile(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public void saveAdmin(Admin admin) {
		Admin existingAdmin = adminRepository.findById(admin.getId()).orElse(null);

		if (existingAdmin != null && !admin.getPassword().equals(existingAdmin.getPassword())) {
			admin.setPassword(passwordEncoder.encode(admin.getPassword()));
		} else if (existingAdmin != null) {
			admin.setPassword(existingAdmin.getPassword());
		}

		if (admin.getStore() != null && admin.getStore().getId() != null) {
			Store store = storeRepository.findById(admin.getStore().getId())
					.orElseThrow(() -> new RuntimeException("Store not found"));
			admin.setStore(store);
			logger.info("Selected Store: {}", store.getStoreName());
		}

		adminRepository.save(admin);
	}

	@Override
	public Admin getCurrentLoggedInAdmin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email;

		if (principal instanceof UserDetails) {
			email = ((UserDetails) principal).getUsername();
		} else {
			email = principal.toString();
		}

		return adminRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("現在のログインユーザーが見つかりません"));
	}
}
