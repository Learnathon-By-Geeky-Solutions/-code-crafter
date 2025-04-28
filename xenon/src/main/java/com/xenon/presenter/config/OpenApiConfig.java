package com.xenon.presenter.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Monowar Hossen Sourav",
                        email = "souravahmednirob@gmail.com",
                        url = "https://souravahmednirob.com/"
                ),
                description = "API Documentation For Xenon",
                title = "Backend",
                version = "1.0.0-SNAPSHOT",
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0"
                )
        ),
        servers = {
                @Server(
                        description = "localhost",
                        url = "http://localhost:8080/"
                ),
                @Server(
                        description = "cloud",
                        url = "https://api.xenonhealthcare.xyz/"
                )
        }
)
@SecuritySchemes(
        value = {
                @SecurityScheme(
                        name = "bearerAuth",
                        description = "JWT web token",
                        scheme = "bearer",
                        type = SecuritySchemeType.HTTP,
                        bearerFormat = "JWT",
                        in = SecuritySchemeIn.HEADER

                ),
        }
)
@Configuration
public class OpenApiConfig {
}