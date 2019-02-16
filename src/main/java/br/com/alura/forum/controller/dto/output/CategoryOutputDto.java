package br.com.alura.forum.controller.dto.output;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.alura.forum.model.Category;

public class CategoryOutputDto {

	@JsonIgnore
	private Category category;
	
	private String categoryName;
	
	private List<String> subCategories;

	private Long allTopics;
	
	private Long lastWeekTopics;
	
	private Long unansweredTopics;
	
	public CategoryOutputDto(Category category) {
		super();
		this.categoryName = category.getName();
		this.subCategories = category.getSubcategoryNames();
	}
		
	public String getCategoryName() {
		return categoryName;
	}

	public List<String> getSubCategories() {
		return subCategories;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public void setSubCategories(List<String> subCategories) {
		this.subCategories = subCategories;
	}


	public Category getCategory() {
		return category;
	}


	public Long getAllTopics() {
		return allTopics;
	}


	public Long getLastWeekTopics() {
		return lastWeekTopics;
	}


	public Long getUnansweredTopics() {
		return unansweredTopics;
	}

	public void setAllTopics(Long allTopics) {
		this.allTopics = allTopics;
	}

	public void setLastWeekTopics(Long lastWeekTopics) {
		this.lastWeekTopics = lastWeekTopics;
	}

	public void setUnansweredTopics(Long unansweredTopics) {
		this.unansweredTopics = unansweredTopics;
	}

	public static List<CategoryOutputDto> listFromCategories(List<Category> categories) {
		return categories.stream().map(CategoryOutputDto::new).collect(Collectors.toList());
	}
}
