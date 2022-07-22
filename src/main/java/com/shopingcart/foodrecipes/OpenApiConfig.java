package com.shopingcart.foodrecipes;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {
	
	@Bean
    public OpenAPI openAPiConfig() {
        ArrayList<Server> servers = new ArrayList<>();
        servers.add(new Server().url("http://localhost:9191").description("Implementation environment"));
        
        return new OpenAPI().info(new Info().title("Recipe Management Service").description("API server for managing recipe details")
                .license(new License().url("http://example.com").name("domain info")).contact(new Contact()
                        .name("domain").email("domain@gmail.com").url("http://example.com")).version("1.0.0")).servers(servers);
    }

}
