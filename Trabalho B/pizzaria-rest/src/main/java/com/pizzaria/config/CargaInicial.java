package com.pizzaria.config;

import com.pizzaria.model.Cliente;
import com.pizzaria.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Cadastra um cliente de exemplo ao iniciar, para já ter o que testar no Swagger. */
@Configuration
public class CargaInicial {

    @Bean
    CommandLineRunner carregarClientes(ClienteRepository repo) {
        return args -> {
            if (repo.count() == 0) {
                Cliente c = new Cliente();
                c.setNome("Joao Silva");
                c.setTelefone("(47) 99999-9999");
                c.setEndereco("Rua A, 123 - Blumenau");
                repo.save(c);
            }
        };
    }
}
