package br.com.alura.forum.controller.dto.input;

import java.util.ArrayList;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;

public class TopicSearchInputDto {
	
	public TopicStatus status;
	public String categoryName;
	
	public TopicStatus getStatus() {
		return status;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setStatus(TopicStatus status) {
		this.status = status;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	public Specification<Topic> build() {
		return (root, criteriaQuery, criteriaBuilder) -> {
			ArrayList<Predicate> predicates = new ArrayList<>();
			if(this.status!=null) {
				predicates.add(criteriaBuilder.equal(root.get("status"), status));
			}
			if(this.categoryName!=null) {
				predicates.add(criteriaBuilder.equal(root
						.get("course")
						.get("subcategory")
						.get("category")
						.get("name"), this.categoryName));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));	
		};
	}
}
