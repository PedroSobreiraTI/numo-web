package com.pedrosobreira.numo.adapter.in.web.dto;

import com.pedrosobreira.numo.domain.model.TipoTransacao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoriaRequest(
        @NotBlank @Size(max = 50) String nome,
        @NotNull TipoTransacao tipo
) {}
