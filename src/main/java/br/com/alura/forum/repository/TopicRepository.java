package br.com.alura.forum.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.controller.dto.input.NewTopicInputDto;
import br.com.alura.forum.model.topic.domain.Topic;
import br.com.alura.forum.model.topic.domain.TopicStatus;

public interface TopicRepository extends Repository<Topic, Long>, JpaSpecificationExecutor<Topic> {

	@Query("select t from Topic t")
	public List<Topic> lista();
	
	@Query("select t from Topic t inner join Course c on t.course.name = c.name inner join Category ca on c.subcategory.name = ca.name where ca.name = :categoria")
	public List<Topic> listaPorCategoria(@Param("categoria") String categoria);
	
	@Query("select t from Topic t inner join Course c on t.course.name = c.name inner join Category ca on c.subcategory.name = ca.name where ca.name = :categoria and t.creationInstant > :instant")
	public List<Topic> listaPorCategoriaECreationInstant(@Param("categoria") String categoria, @Param("instant")Instant instant);
	
	@Query("select t from Topic t inner join Course c on t.course.name = c.name inner join Category ca on c.subcategory.name = ca.name where ca.name = :categoria and t.status = :status")
	public List<Topic> listaPorCategoriaEStatus(@Param("categoria") String categoria, @Param("status")TopicStatus status);
	
	public Page<Topic> findAll(Specification<Topic> topicSearchSpecification, Pageable pageable);

	public void save(Topic topic);
}
