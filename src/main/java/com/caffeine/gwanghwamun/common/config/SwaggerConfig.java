package com.caffeine.gwanghwamun.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {

		SecurityRequirement securityRequirement = new SecurityRequirement().addList("accessToken");
		Components components =
				new Components()
						.addSecuritySchemes(
								"accessToken",
								new SecurityScheme()
										.name("accessToken")
										.type(SecurityScheme.Type.HTTP)
										.scheme("bearer")
										.bearerFormat("JWT"));

		return new OpenAPI()
				.info(apiInfo())
				.servers(servers())
				.components(components)
				.addSecurityItem(securityRequirement);
	}

	private Info apiInfo() {
		return new Info().title("광화문의 민족 API").description("배달 주문 관리 플랫폼 API 문서").version("v1.0.0");
	}

	private List<Server> servers() {
		Server local = new Server();
		Server deploy = new Server();

		local.setUrl("http://localhost:8081");
		local.setDescription("백엔드 로컬");

		deploy.setUrl("https://the-people-of-gwangh.kro.kr");
		deploy.setDescription("백엔드 배포");

		return List.of(local, deploy);
	}
}
