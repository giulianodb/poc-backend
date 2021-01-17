package com.giulianodb.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Header;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	private final ResponseMessage m201 = customMessage1();
	private final ResponseMessage m204put = simpleMessage(204,"Mise à jour OK");
	private final ResponseMessage m204del = simpleMessage(204,"Délétion OK");
	private final ResponseMessage m404 = simpleMessage(404,"Non trouvée.");
	private final ResponseMessage m500 = simpleMessage(500,"Erreur inconnue");

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.useDefaultResponseMessages(false)
				.globalResponseMessage(RequestMethod.GET, Arrays.asList(m404, m500))
				.globalResponseMessage(RequestMethod.POST, Arrays.asList(m201,m500))
				.globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put,m404, m500))
				.globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del,m404, m500))
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.giulianodb.resources"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("API POC du SpringBoot",
				"Cette Api est pour une preuve de concept de SpringBoot. Giuliano Diego Barbarra",
				"Version 1.0",
				"https://github.com/terms",
				new Contact("Giuliano Diego Barbarra", 
						"https://www.linkedin.com/in/giuliano-diego-barbarra", "giulianodb@gmail.com"),
				"Pour tous", "https://github.com/terms", Collections.emptyList() // Vendor
																										// Extensions
		);
	}
	
	private ResponseMessage simpleMessage(int code, String msg) {
		return new ResponseMessageBuilder().code(code).message(msg).build();
	}
	
	private ResponseMessage customMessage1() {
		Map<String, Header> map = new HashMap<>();
		map.put("location", new Header("location", "URI de la nouvelle ressource", new ModelRef("string")));
		return new ResponseMessageBuilder()
		.code(201)
		.message("Ressource Créée")
		.headersWithDescription(map)
		.build();
	}
}