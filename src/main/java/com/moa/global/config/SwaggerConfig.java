package com.moa.global.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;


@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

	@Bean
	public GroupedOpenApi publicApi() {

		String[] paths = { "/api/v1/**" };

		return GroupedOpenApi.builder()
			.group("meeting-api")
			.pathsToMatch(paths)
			.build();
	}


	@Bean
	public OpenAPI openAPI() {
		// Security 스키마 설정
		SecurityScheme bearerAuth = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.scheme("Bearer")
			.bearerFormat("Authorization")
			.in(SecurityScheme.In.HEADER)
			.name(HttpHeaders.AUTHORIZATION);

		// Security 요청 설정
		SecurityRequirement addSecurityItem = new SecurityRequirement();
		addSecurityItem.addList("Authorization");

		Info info = new Info()
			.version("v1.0.0")
			.title("MOA Meeting API")
			.description("moa meeting service");

		return new OpenAPI()
			.addServersItem(new Server().url("/"))
			.info(info)
			.components(new Components().addSecuritySchemes("Authorization", bearerAuth))
			.security(List.of(addSecurityItem));
	}

}