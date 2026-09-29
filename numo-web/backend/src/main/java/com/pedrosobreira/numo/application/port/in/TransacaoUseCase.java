package com.pedrosobreira.numo.application.port.in;

import com.pedrosobreira.numo.domain.model.ResumoFinanceiro;
import com.pedrosobreira.numo.domain.model.TipoTransacao;
import com.pedrosobreira.numo.domain.model.Transacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/** Porta de entrada: o que o mundo externo pode pedir sobre transações. */
public interface TransacaoUseCase {

    Transacao registrar(RegistrarTransacaoCommand command);

    List<Transacao> listar();

    void remover(UUID id);

    ResumoFinanceiro resumo();

    record RegistrarTransacaoCommand(String descricao, BigDecimal valor, TipoTransacao tipo,
                                     LocalDate data, UUID categoriaId) {}
}
