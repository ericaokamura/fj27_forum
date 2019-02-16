package br.com.alura.forum.controller;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.output.CategoryOutputDto;
import br.com.alura.forum.model.Category;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;
import br.com.alura.forum.repository.CategoryRepository;
import br.com.alura.forum.repository.TopicRepository;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private TopicRepository topicRepository;
	
	@GetMapping(value = "/api/topics/dashboard", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CategoryOutputDto> listCategories() {
	
		List<Category> categories = categoryRepository.findAll();
		List<CategoryOutputDto> outputDto = CategoryOutputDto.listFromCategories(categories);
	
		for (CategoryOutputDto categoryOutputDto : outputDto) {
		
			Category categoriaPrincipal = categoryOutputDto.getCategory();
			
			if(categoriaPrincipal == null){
				List<Topic> topicsPorCategoria = topicRepository.listaPorCategoria(categoryOutputDto.getCategoryName());
				categoryOutputDto.setAllTopics((long) topicsPorCategoria.size());
				List<Topic> topicsPorCreationInstant = topicRepository.listaPorCategoriaECreationInstant(categoryOutputDto.getCategoryName(), Instant.now().minus(7, ChronoUnit.DAYS));
				categoryOutputDto.setLastWeekTopics((long) topicsPorCreationInstant.size()); 
				List<Topic> topicsPorStatus = topicRepository.listaPorCategoriaEStatus(categoryOutputDto.getCategoryName(), TopicStatus.NOT_ANSWERED);
				categoryOutputDto.setUnansweredTopics((long) topicsPorStatus.size()); 
			}
		}
		return outputDto;
	}

}
