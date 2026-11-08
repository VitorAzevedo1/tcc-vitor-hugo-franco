package br.com.pucgo.saudematuridade.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API — Sistema de Organização de Informações de Saúde na Maturidade")
                        .version("1.0.0")
                        .description("API REST do TCC: organização, visualização e compartilhamento seguro de informações pessoais de saúde da pessoa idosa, com compartilhamento controlado para familiares autorizados.")
                        .contact(new Contact()
                                .name("Vitor Hugo Azevedo")
                                .email("vitorhugoazevedo209@gmail.com"))
                        .license(new License()
                                .name("Uso acadêmico - TCC PUC GO 2026")))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Cole o token JWT retornado pelo endpoint POST /api/auth/login")));
    }
}