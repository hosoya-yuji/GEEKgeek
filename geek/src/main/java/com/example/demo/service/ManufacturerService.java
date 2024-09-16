package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Manufacturer;

public interface ManufacturerService {
	List<Manufacturer> findAll();

	Manufacturer findById(Long id);

	Manufacturer save(Manufacturer manufacturer);

	void deleteById(Long id);


	List<Manufacturer> getAllManufacturers();
}
