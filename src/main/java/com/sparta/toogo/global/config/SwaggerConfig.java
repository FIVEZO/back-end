package com.sparta.toogo.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "오늘 이곳(OE) Project API",
                description = "5조 실전 프로젝트 오늘 이곳(OE) Project API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {
}
