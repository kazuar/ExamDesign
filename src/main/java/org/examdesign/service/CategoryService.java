package org.examdesign.service;

import java.util.List;

import org.examdesign.domain.Category;
import org.examdesign.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public Boolean create(Category category) {
		
		Category savedCategory = categoryRepository.save(category);
		if (savedCategory == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean update(Category category) {
		
		Category existingCategory = categoryRepository.findOne(category.getId());
		
		if (existingCategory == null) {
			return false;
		}
		
		existingCategory.setCategoryName(category.getCategoryName());
		
		Category savedCategory = categoryRepository.save(existingCategory);
		if (savedCategory == null) {
			return false;
		}
		
		return true;
	}
	
	public Boolean delete(Category category) {
		
		Category existingCategory = categoryRepository.findOne(category.getId());
		
		if (existingCategory == null) {
			return false;
		}
		
		categoryRepository.delete(existingCategory);
		
		Category deletedCategory = categoryRepository.findOne(category.getId());
		if (deletedCategory != null) {
			return false;
		}
		
		return true;
	}
	
	public String list() {
		List<Category> categories = categoryRepository.findAll();
		String categoriesList = "";
		
		for (Category category : categories) {
			categoriesList += "" + category.getId() + ":" + category.getCategoryName();
			categoriesList += (categories.indexOf(category) < (categories.size() - 1) ? ";" : "");
		}
		
		return categoriesList;
	}
}
