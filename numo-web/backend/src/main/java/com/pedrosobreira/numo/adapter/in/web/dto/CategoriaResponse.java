package com.pedrosobreira.numo.adapter.in.web.dto;

import com.pedrosobreira.numo.domain.model.Categoria;
import com.pedrosobreira.numo.domain.model.TipoTransacao;

import java.util.UUID;

public record CategoriaResponse(UUID id, String nome, TipoTransacao tipo) {
    public static CategoriaResponse from(Categoria c) {
        return new CategoriaResponse(c.getId(), c.getNome(), c.getTipo());
    }
}
