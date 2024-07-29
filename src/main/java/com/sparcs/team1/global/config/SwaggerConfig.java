package com.sparcs.team1.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .title("SPARCS AI Startup Hackathon 2024, 대상은 우리 거")
                .description("레전드 개발자 윤서진 화이팅")
                .version("v1");

        return new OpenAPI()
                .addServersItem(new Server().url("/"))
                .info(info);
    }
}
