package com.caffeine.gwanghwamun.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("광화문의 민족 API")
                        .description("배달 주문 관리 플랫폼 API 문서")
                        .version("v1.0.0")
                        );
    }

}
