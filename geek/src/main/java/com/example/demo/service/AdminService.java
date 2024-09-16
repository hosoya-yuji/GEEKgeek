package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Admin;

public interface AdminService {
	List<Admin> findAll();

	Admin findById(Long id);

	Admin save(Admin admin);

	void deleteById(Long id);

	Admin findByEmail(String email); // プロフィール管理で必要

	Admin updateAdminProfile(Admin admin); // プロフィール編集で必要

	void saveAdmin(Admin admin);

	Admin getCurrentLoggedInAdmin(); // 現在のログインユーザーを取得するメソッドを追加
}
