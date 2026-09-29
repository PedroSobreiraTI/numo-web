package com.pedrosobreira.numo.application.service;

import com.pedrosobreira.numo.application.port.in.CategoriaUseCase;
import com.pedrosobreira.numo.application.port.out.CategoriaRepositoryPort;
import com.pedrosobreira.numo.domain.exception.RegraDeNegocioException;
import com.pedrosobreira.numo.domain.model.Categoria;

import java.util.Comparator;
import java.util.List;

/** Implementa o caso de uso sem depender de framework. O Spring só injeta via config. */
public class CategoriaService implements CategoriaUseCase {

    private final CategoriaRepositoryPort repository;

    public CategoriaService(CategoriaRepositoryPort repository) {
        this.repository = repository;
    }

    @Override
    public Categoria criar(CriarCategoriaCommand command) {
        if (command.nome() != null && repository.existePorNome(command.nome().trim())) {
            throw new RegraDeNegocioException("Já existe uma categoria com esse nome");
        }
        return repository.salvar(Categoria.nova(command.nome(), command.tipo()));
    }

    @Override
    public List<Categoria> listar() {
        return repository.listarTodas().stream()
                .sorted(Comparator.comparing(Categoria::getNome, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }
}
