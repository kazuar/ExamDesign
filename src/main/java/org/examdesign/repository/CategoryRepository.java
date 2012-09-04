package org.examdesign.repository;

import org.examdesign.domain.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	
	Category findByCategoryName(String categoryName);
	
	Page<Category> findByCategoryNameLike(String categoryName, Pageable pageable);
}
