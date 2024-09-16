package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Position;

public interface PositionService {
	List<Position> findAll();

	Position findByPositionName(String positionName);

	Position findById(Long id);
}
