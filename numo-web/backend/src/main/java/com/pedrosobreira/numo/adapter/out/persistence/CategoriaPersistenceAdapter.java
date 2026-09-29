package com.pedrosobreira.numo.adapter.out.persistence;

import com.pedrosobreira.numo.application.port.out.CategoriaRepositoryPort;
import com.pedrosobreira.numo.domain.model.Categoria;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Adapter de saída: traduz entre o domínio e o JPA. Trocar de banco = trocar só esta classe. */
@Component
public class CategoriaPersistenceAdapter implements CategoriaRepositoryPort {

    private final CategoriaJpaRepository jpa;

    public CategoriaPersistenceAdapter(CategoriaJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Categoria salvar(Categoria categoria) {
        return toDomain(jpa.save(new CategoriaJpaEntity(
                categoria.getId(), categoria.getNome(), categoria.getTipo())));
    }

    @Override
    public Optional<Categoria> buscarPorId(UUID id) {
        return jpa.findById(id).map(this::toDomain);
    }

    @Override
    public List<Categoria> listarTodas() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existePorNome(String nome) {
        return jpa.existsByNomeIgnoreCase(nome);
    }

    private Categoria toDomain(CategoriaJpaEntity e) {
        return Categoria.restaurar(e.getId(), e.getNome(), e.getTipo());
    }
}
