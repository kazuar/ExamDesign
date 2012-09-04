package org.examdesign.util;

import java.util.ArrayList;
import java.util.List;

import org.examdesign.domain.Category;
import org.examdesign.response.CategoryDto;
import org.springframework.data.domain.Page;

public class CategoryMapper {
	
	public static CategoryDto map(Category category) {
		CategoryDto dto = new CategoryDto();
		dto.setId(category.getId());
		dto.setCategoryName(category.getCategoryName());
		return dto;
	}
	
	public static List<CategoryDto> map(Page<Category> categories) {
		
		List<CategoryDto> dtos = new ArrayList<CategoryDto>();
		for (Category category : categories) {
			dtos.add(map(category));
		}
		
		return dtos;
	}
}
