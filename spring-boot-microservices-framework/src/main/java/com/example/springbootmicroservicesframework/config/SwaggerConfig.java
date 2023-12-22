package com.example.springbootmicroservicesframework.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        servers = {
                @Server(url = "${swagger.server.url}")
        },
        info = @Info(title = "${swagger.info.title}", version = "${swagger.info.version}", description = "${swagger.info.description}")
)
@SecurityScheme(
        name = "${swagger.security-scheme.name}",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "${swagger.security-scheme.bearer-format}",
        scheme = "${swagger.security-scheme.scheme}"
)
public class SwaggerConfig {
}
