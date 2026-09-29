package com.pedrosobreira.numo.domain.model;

import java.math.BigDecimal;
import java.util.List;

public record ResumoFinanceiro(BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal saldo) {

    public static ResumoFinanceiro de(List<Transacao> transacoes) {
        BigDecimal receitas = somar(transacoes, TipoTransacao.RECEITA);
        BigDecimal despesas = somar(transacoes, TipoTransacao.DESPESA);
        return new ResumoFinanceiro(receitas, despesas, receitas.subtract(despesas));
    }

    private static BigDecimal somar(List<Transacao> transacoes, TipoTransacao tipo) {
        return transacoes.stream()
                .filter(t -> t.getTipo() == tipo)
                .map(Transacao::getValor)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
