package com.example.demo.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "medium_category")
public class MediumCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "medium_category_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "large_category_id")
	private LargeCategory largeCategory;

	@Column(name = "medium_category_name")
	private String name;

	@OneToMany(mappedBy = "mediumCategory", fetch = FetchType.LAZY)
	private List<SmallCategory> smallCategories;
}
