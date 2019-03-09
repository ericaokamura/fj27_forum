package br.com.alura.forum.controller;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.input.NewTopicInputDto;
import br.com.alura.forum.controller.dto.input.TopicSearchInputDto;
import br.com.alura.forum.controller.dto.output.TopicBriefOutputDto;
import br.com.alura.forum.dto.output.TopicOutputDto;
import br.com.alura.forum.model.User;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.validator.NewTopicCustomValidator;

@RestController
public class TopicController {
	
	@Autowired
	private TopicRepository topicRepository;
	
	@Autowired
	private CourseRepository courseRepository;
	
	@GetMapping(value = "/api/topics", produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<TopicBriefOutputDto> listTopics(TopicSearchInputDto inputDto,
			@PageableDefault(sort= {"shortDescription", "creationInstant"}, direction=Sort.Direction.DESC) Pageable pageable) {
		Specification<Topic> topicSearchSpecification = inputDto.build();
		Page<Topic> topics = topicRepository.findAll(topicSearchSpecification, pageable);
		return TopicBriefOutputDto.listFromTopics(topics);
	}
	
	@PostMapping(value = "/api/topics", consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createTopic (@Valid @RequestBody NewTopicInputDto newTopic,
			@AuthenticationPrincipal User loggedUser, UriComponentsBuilder uriBuilder) {
		Topic topic = newTopic.build(loggedUser, this.courseRepository);
		this.topicRepository.save(topic);
		URI path =  uriBuilder.path("/api/topics/{id}").buildAndExpand(topic.getId()).toUri();
		return ResponseEntity.created(path).body(new TopicOutputDto(topic));
	}
	
	@InitBinder("newTopicInputDto")
	public void initBinder(WebDataBinder binder, @AuthenticationPrincipal User loggedUser) {
		binder.addValidators(new NewTopicCustomValidator(this.topicRepository, loggedUser));
	}
} 
