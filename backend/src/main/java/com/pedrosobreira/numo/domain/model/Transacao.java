package com.pedrosobreira.numo.domain.model;

import com.pedrosobreira.numo.domain.exception.RegraDeNegocioException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade de domínio. As regras de negócio moram aqui, não no controller nem no banco.
 */
public class Transacao {

    private final UUID id;
    private final String descricao;
    private final BigDecimal valor;
    private final TipoTransacao tipo;
    private final LocalDate data;
    private final UUID categoriaId;

    private Transacao(UUID id, String descricao, BigDecimal valor, TipoTransacao tipo,
                      LocalDate data, UUID categoriaId) {
        if (descricao == null || descricao.isBlank()) {
            throw new RegraDeNegocioException("Descrição é obrigatória");
        }
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new RegraDeNegocioException("Valor deve ser maior que zero");
        }
        if (tipo == null) {
            throw new RegraDeNegocioException("Tipo da transação é obrigatório");
        }
        if (data == null) {
            throw new RegraDeNegocioException("Data é obrigatória");
        }
        this.id = Objects.requireNonNull(id);
        this.descricao = descricao.trim();
        this.valor = valor;
        this.tipo = tipo;
        this.data = data;
        this.categoriaId = Objects.requireNonNull(categoriaId, "Categoria é obrigatória");
    }

    public static Transacao nova(String descricao, BigDecimal valor, TipoTransacao tipo,
                                 LocalDate data, Categoria categoria) {
        if (categoria == null) {
            throw new RegraDeNegocioException("Categoria é obrigatória");
        }
        if (!categoria.aceita(tipo)) {
            throw new RegraDeNegocioException(
                    "Categoria '" + categoria.getNome() + "' não aceita transações do tipo " + tipo);
        }
        return new Transacao(UUID.randomUUID(), descricao, valor, tipo, data, categoria.getId());
    }

    public static Transacao restaurar(UUID id, String descricao, BigDecimal valor, TipoTransacao tipo,
                                      LocalDate data, UUID categoriaId) {
        return new Transacao(id, descricao, valor, tipo, data, categoriaId);
    }

    /** Valor com sinal: positivo para receita, negativo para despesa. */
    public BigDecimal valorComSinal() {
        return tipo == TipoTransacao.RECEITA ? valor : valor.negate();
    }

    public UUID getId() { return id; }
    public String getDescricao() { return descricao; }
    public BigDecimal getValor() { return valor; }
    public TipoTransacao getTipo() { return tipo; }
    public LocalDate getData() { return data; }
    public UUID getCategoriaId() { return categoriaId; }
}
