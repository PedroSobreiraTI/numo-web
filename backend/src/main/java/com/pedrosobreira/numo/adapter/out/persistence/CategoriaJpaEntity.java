package com.pedrosobreira.numo.adapter.out.persistence;

import com.pedrosobreira.numo.domain.model.TipoTransacao;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "categorias")
public class CategoriaJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoTransacao tipo;

    protected CategoriaJpaEntity() {}

    public CategoriaJpaEntity(UUID id, String nome, TipoTransacao tipo) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
    }

    public UUID getId() { return id; }
    public String getNome() { return nome; }
    public TipoTransacao getTipo() { return tipo; }
}
