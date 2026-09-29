package com.pedrosobreira.numo.config;

import com.pedrosobreira.numo.application.port.in.CategoriaUseCase;
import com.pedrosobreira.numo.application.port.in.TransacaoUseCase;
import com.pedrosobreira.numo.application.port.out.CategoriaRepositoryPort;
import com.pedrosobreira.numo.application.port.out.TransacaoRepositoryPort;
import com.pedrosobreira.numo.application.service.CategoriaService;
import com.pedrosobreira.numo.application.service.TransacaoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Único lugar que "liga" o núcleo ao Spring.
 * Os services não têm @Service: o domínio e a aplicação ficam livres do framework.
 */
@Configuration
public class BeanConfig {

    @Bean
    public CategoriaUseCase categoriaUseCase(CategoriaRepositoryPort categoriaRepository) {
        return new CategoriaService(categoriaRepository);
    }

    @Bean
    public TransacaoUseCase transacaoUseCase(TransacaoRepositoryPort transacaoRepository,
                                             CategoriaRepositoryPort categoriaRepository) {
        return new TransacaoService(transacaoRepository, categoriaRepository);
    }
}
