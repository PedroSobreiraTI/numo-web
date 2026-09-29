package com.pedrosobreira.numo.adapter.out.persistence;

import com.pedrosobreira.numo.application.port.out.TransacaoRepositoryPort;
import com.pedrosobreira.numo.domain.model.Transacao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class TransacaoPersistenceAdapter implements TransacaoRepositoryPort {

    private final TransacaoJpaRepository jpa;

    public TransacaoPersistenceAdapter(TransacaoJpaRepository jpa) {
        this.jpa = jpa;
    }

    @Override
    public Transacao salvar(Transacao t) {
        return toDomain(jpa.save(new TransacaoJpaEntity(
                t.getId(), t.getDescricao(), t.getValor(), t.getTipo(), t.getData(), t.getCategoriaId())));
    }

    @Override
    public List<Transacao> listarTodas() {
        return jpa.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existePorId(UUID id) {
        return jpa.existsById(id);
    }

    @Override
    public void remover(UUID id) {
        jpa.deleteById(id);
    }

    private Transacao toDomain(TransacaoJpaEntity e) {
        return Transacao.restaurar(e.getId(), e.getDescricao(), e.getValor(), e.getTipo(),
                e.getData(), e.getCategoriaId());
    }
}
