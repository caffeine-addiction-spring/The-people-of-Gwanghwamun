package com.caffeine.gwanghwamun.common.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import java.util.Map;
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
				.addSecurityItem(securityRequirement)
				.paths(customPaths());
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

	private Paths customPaths() {
		Paths paths = new Paths();

		PathItem loginPath =
				new PathItem()
						.post(
								new Operation()
										.tags(List.of("인증"))
										.summary("로그인 API")
										.description("이메일, 비밀번호를 통한 로그인")
										.requestBody(
												new RequestBody()
														.content(
																new Content()
																		.addMediaType(
																				"application/json",
																				new MediaType()
																						.schema(
																								new Schema<>()
																										.type("object")
																										.addProperty(
																												"email",
																												new Schema<>()
																														.type("string")
																														.example("user@example.com"))
																										.addProperty(
																												"password",
																												new Schema<>()
																														.type("string")
																														.example("password123"))))))
										.responses(
												new ApiResponses()
														.addApiResponse(
																"200",
																new ApiResponse()
																		.description("로그인 성공")
																		.headers(
																				Map.of(
																						"Authorization",
																						new Header()
																								.description("JWT 토큰")
																								.schema(new Schema<>().type("string")))))));

		paths.addPathItem("/v1/auth/login", loginPath);
		return paths;
	}
}
