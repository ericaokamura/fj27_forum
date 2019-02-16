package br.com.alura.forum.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
	
	@Bean
	public Docket api() {
		Contact contato = new Contact("Alura",
				"https://cursos.alura.com.br/", "contato@alura.com.br");
		return new Docket(DocumentationType.SWAGGER_2)
					.select()
					.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))
					.paths(PathSelectors.ant("/api/**"))
					.build()
					.apiInfo(new ApiInfoBuilder()
							.title("Alura Forum API Documentation")
							.description("Esta é a documentação interativa da Rest API do Fórum da Alura. Tente"
									+ " enviar algum request ;)")
							.version("1.0")
							.contact(contato)
							.build());
	}
}