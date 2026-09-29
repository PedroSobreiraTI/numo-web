package com.pedrosobreira.numo.adapter.in.web.dto;

import com.pedrosobreira.numo.domain.model.TipoTransacao;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TransacaoRequest(
        @NotBlank @Size(max = 120) String descricao,
        @NotNull @Positive @Digits(integer = 10, fraction = 2) BigDecimal valor,
        @NotNull TipoTransacao tipo,
        @NotNull LocalDate data,
        @NotNull UUID categoriaId
) {}
