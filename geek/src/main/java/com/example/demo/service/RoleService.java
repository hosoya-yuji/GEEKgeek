package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Role;

public interface RoleService {
	List<Role> findAll();

	Role findByRoleName(String roleName);

	Role findById(Long id);
}
