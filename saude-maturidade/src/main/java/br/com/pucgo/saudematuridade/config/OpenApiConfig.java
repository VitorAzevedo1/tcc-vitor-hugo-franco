package br.com.pucgo.saudematuridade.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

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
                                .name("Uso acadêmico - TCC PUC GO 2026")));
    }
}
