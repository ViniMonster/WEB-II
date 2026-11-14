package com.pizzaria.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI pizzariaOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API REST - Pizzaria")
                .version("1.0")
                .description("Web Service REST de uma pizzaria: clientes e pedidos (um cliente tem vários pedidos), "
                        + "incluindo cancelamento de pedido (ex.: pizza veio errada)."));
    }
}
