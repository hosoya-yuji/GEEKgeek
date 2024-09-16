package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Position;
import com.example.demo.repository.PositionRepository;
import com.example.demo.service.PositionService;

@Service
public class PositionServiceImpl implements PositionService {

	private final PositionRepository positionRepository;

	@Autowired
	public PositionServiceImpl(PositionRepository positionRepository) {
		this.positionRepository = positionRepository;
	}

	@Override
	public List<Position> findAll() {
		return positionRepository.findAll();
	}

	@Override
	public Position findByPositionName(String positionName) {
		return positionRepository.findByPositionName(positionName)
				.orElseThrow(() -> new RuntimeException("Position not found"));
	}

	@Override
	public Position findById(Long id) {
		return positionRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Position not found"));
	}
}
