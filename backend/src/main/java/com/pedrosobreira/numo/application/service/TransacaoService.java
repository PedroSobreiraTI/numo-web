package com.pedrosobreira.numo.application.service;

import com.pedrosobreira.numo.application.port.in.TransacaoUseCase;
import com.pedrosobreira.numo.application.port.out.CategoriaRepositoryPort;
import com.pedrosobreira.numo.application.port.out.TransacaoRepositoryPort;
import com.pedrosobreira.numo.domain.exception.RecursoNaoEncontradoException;
import com.pedrosobreira.numo.domain.model.Categoria;
import com.pedrosobreira.numo.domain.model.ResumoFinanceiro;
import com.pedrosobreira.numo.domain.model.Transacao;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class TransacaoService implements TransacaoUseCase {

    private final TransacaoRepositoryPort transacaoRepository;
    private final CategoriaRepositoryPort categoriaRepository;

    public TransacaoService(TransacaoRepositoryPort transacaoRepository,
                            CategoriaRepositoryPort categoriaRepository) {
        this.transacaoRepository = transacaoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Transacao registrar(RegistrarTransacaoCommand command) {
        Categoria categoria = categoriaRepository.buscarPorId(command.categoriaId())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Categoria não encontrada"));

        Transacao transacao = Transacao.nova(
                command.descricao(), command.valor(), command.tipo(), command.data(), categoria);

        return transacaoRepository.salvar(transacao);
    }

    @Override
    public List<Transacao> listar() {
        return transacaoRepository.listarTodas().stream()
                .sorted(Comparator.comparing(Transacao::getData).reversed())
                .toList();
    }

    @Override
    public void remover(UUID id) {
        if (!transacaoRepository.existePorId(id)) {
            throw new RecursoNaoEncontradoException("Transação não encontrada");
        }
        transacaoRepository.remover(id);
    }

    @Override
    public ResumoFinanceiro resumo() {
        return ResumoFinanceiro.de(transacaoRepository.listarTodas());
    }
}
