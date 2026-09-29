package com.pedrosobreira.numo.application.port.out;

import com.pedrosobreira.numo.domain.model.Transacao;

import java.util.List;
import java.util.UUID;

public interface TransacaoRepositoryPort {
    Transacao salvar(Transacao transacao);
    List<Transacao> listarTodas();
    boolean existePorId(UUID id);
    void remover(UUID id);
}
