package com.example.demo.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Manufacturer;
import com.example.demo.repository.ManufacturerRepository;
import com.example.demo.service.ManufacturerService;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

	private final ManufacturerRepository manufacturerRepository;

	@Autowired
	public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
		this.manufacturerRepository = manufacturerRepository;
	}

	@Override
	public List<Manufacturer> findAll() {
		return manufacturerRepository.findAll();
	}

	@Override
	public Manufacturer findById(Long id) {
		return manufacturerRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Manufacturer not found"));
	}

	@Override
	public Manufacturer save(Manufacturer manufacturer) {
		return manufacturerRepository.save(manufacturer);
	}

	@Override
	public void deleteById(Long id) {
		manufacturerRepository.deleteById(id);
	}

	@Override
	public List<Manufacturer> getAllManufacturers() {
		return manufacturerRepository.findAll();
	}
}
