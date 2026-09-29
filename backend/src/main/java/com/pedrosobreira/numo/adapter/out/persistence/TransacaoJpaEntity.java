package com.pedrosobreira.numo.adapter.out.persistence;

import com.pedrosobreira.numo.domain.model.TipoTransacao;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "transacoes")
public class TransacaoJpaEntity {

    @Id
    private UUID id;

    @Column(nullable = false, length = 120)
    private String descricao;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoTransacao tipo;

    @Column(nullable = false)
    private LocalDate data;

    @Column(name = "categoria_id", nullable = false)
    private UUID categoriaId;

    protected TransacaoJpaEntity() {}

    public TransacaoJpaEntity(UUID id, String descricao, BigDecimal valor, TipoTransacao tipo,
                              LocalDate data, UUID categoriaId) {
        this.id = id;
        this.descricao = descricao;
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
        this.categoriaId = categoriaId;
    }

    public UUID getId() { return id; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public TipoTransacao getTipo() { return tipo; }
    public LocalDate getData() { return data; }
    public UUID getCategoriaId() { return categoriaId; }
}
