package com.pedrosobreira.numo.application.port.out;

import com.pedrosobreira.numo.domain.model.Categoria;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Porta de saída: o domínio diz o que precisa, o adapter decide como (JPA, memória, API...). */
public interface CategoriaRepositoryPort {
    Categoria salvar(Categoria categoria);
    Optional<Categoria> buscarPorId(UUID id);
    List<Categoria> listarTodas();
    boolean existePorNome(String nome);
}
