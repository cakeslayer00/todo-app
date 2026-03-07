package io.cakeslayer.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Todo App Backend API",
                version = "1.0.0",
                description = "Backend API for Todo Application with JWT Authentication",
                contact = @Contact(
                        name = "API Support",
                        url = "https://github.com/cakeslayer00/todo-app-fortnite-style"
                )
        ),
        servers = {
                @Server(
                        url = "http://localhost:8080",
                        description = "Development Server"
                )
        }
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        description = "JWT Bearer Token Authentication. Include in Authorization header as: Bearer <token>"
)
public class SwaggerConfig implements WebMvcConfigurer {
}




