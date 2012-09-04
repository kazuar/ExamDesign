package org.examdesign.response;

import java.io.Serializable;

public class CategoryDto implements Serializable {
	
	private Long id;
	private String categoryName;
	
	public Long getId() {
		return id;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
