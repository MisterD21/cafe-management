package com.inn.cafe.wrapper;

import java.util.List;

import com.inn.cafe.entities.Category;

public class CategoriesDto {
	
	private List<Category> categories;
	
	public List<Category> getCategories() {
		return categories;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	@Override
	public String toString() {
		return "CategoriesDto [categories=" + categories + "]";
	}
	

}
