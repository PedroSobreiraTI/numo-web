package com.pedrosobreira.numo.domain.model;

import com.pedrosobreira.numo.domain.exception.RegraDeNegocioException;

import java.util.Objects;
import java.util.UUID;

/**
 * Entidade de domínio. Não conhece Spring, JPA nem HTTP.
 */
public class Categoria {

    private final UUID id;
    private final String nome;
    private final TipoTransacao tipo;

    private Categoria(UUID id, String nome, TipoTransacao tipo) {
        if (nome == null || nome.isBlank()) {
            throw new RegraDeNegocioException("Nome da categoria é obrigatório");
        }
        if (nome.length() > 50) {
            throw new RegraDeNegocioException("Nome da categoria deve ter no máximo 50 caracteres");
        }
        if (tipo == null) {
            throw new RegraDeNegocioException("Tipo da categoria é obrigatório");
        }
        this.id = Objects.requireNonNull(id);
        this.nome = nome.trim();
        this.tipo = tipo;
    }

    public static Categoria nova(String nome, TipoTransacao tipo) {
        return new Categoria(UUID.randomUUID(), nome, tipo);
    }

    public static Categoria restaurar(UUID id, String nome, TipoTransacao tipo) {
        return new Categoria(id, nome, tipo);
    }

    public boolean aceita(TipoTransacao tipoTransacao) {
        return this.tipo == tipoTransacao;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public TipoTransacao getTipo() { return tipo; }
}
