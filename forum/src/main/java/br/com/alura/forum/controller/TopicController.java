package br.com.alura.forum.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.alura.forum.controller.dto.input.TopicSearchInputDto;
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDto;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.TopicRepository;

@RestController
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;
	
	@RequestMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<TopicBriefOutputDto> listTopics(TopicSearchInputDto inputDto) {
		Specification<Topic> topicSearchSpecification = inputDto.build();
		List<Topic> topics = topicRepository.findAll(topicSearchSpecification);
		return TopicBriefOutputDto.listFromTopics(topics);
	}
} 
