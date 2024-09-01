package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.MediumCategory;

public interface MediumCategoryRepository extends JpaRepository<MediumCategory, Long> {

	List<MediumCategory> findByLargeCategoryId(Long largeCategoryId);

	@Query("SELECT mc FROM MediumCategory mc LEFT JOIN FETCH mc.smallCategories WHERE mc.id = :id")
	Optional<MediumCategory> findByIdWithSmallCategories(@Param("id") Long id);
}
