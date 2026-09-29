package com.pedrosobreira.numo.adapter.in.web.dto;

import com.pedrosobreira.numo.domain.model.TipoTransacao;
import com.pedrosobreira.numo.domain.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoResponse(UUID id, String descricao, BigDecimal valor, TipoTransacao tipo,
                                LocalDate data, UUID categoriaId) {
    public static TransacaoResponse from(Transacao t) {
        return new TransacaoResponse(t.getId(), t.getDescricao(), t.getValor(), t.getTipo(),
                t.getData(), t.getCategoriaId());
    }
}
