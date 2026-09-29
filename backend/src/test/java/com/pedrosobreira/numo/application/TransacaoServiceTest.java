package com.pedrosobreira.numo.application;

import com.pedrosobreira.numo.application.port.in.TransacaoUseCase.RegistrarTransacaoCommand;
import com.pedrosobreira.numo.application.port.out.CategoriaRepositoryPort;
import com.pedrosobreira.numo.application.port.out.TransacaoRepositoryPort;
import com.pedrosobreira.numo.application.service.TransacaoService;
import com.pedrosobreira.numo.domain.exception.RecursoNaoEncontradoException;
import com.pedrosobreira.numo.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa o caso de uso trocando o banco por adapters em memória.
 * É a vantagem prática da hexagonal: o núcleo não sabe que o JPA existe.
 */
class TransacaoServiceTest {

    private final Map<UUID, Categoria> categorias = new HashMap<>();
    private final Map<UUID, Transacao> transacoes = new LinkedHashMap<>();
    private TransacaoService service;
    private Categoria salario;
    private Categoria mercado;

    @BeforeEach
    void setUp() {
        CategoriaRepositoryPort categoriaRepo = new CategoriaRepositoryPort() {
            public Categoria salvar(Categoria c) { categorias.put(c.getId(), c); return c; }
            public Optional<Categoria> buscarPorId(UUID id) { return Optional.ofNullable(categorias.get(id)); }
            public List<Categoria> listarTodas() { return new ArrayList<>(categorias.values()); }
            public boolean existePorNome(String nome) {
                return categorias.values().stream().anyMatch(c -> c.getNome().equalsIgnoreCase(nome));
            }
        };
        TransacaoRepositoryPort transacaoRepo = new TransacaoRepositoryPort() {
            public Transacao salvar(Transacao t) { transacoes.put(t.getId(), t); return t; }
            public List<Transacao> listarTodas() { return new ArrayList<>(transacoes.values()); }
            public boolean existePorId(UUID id) { return transacoes.containsKey(id); }
            public void remover(UUID id) { transacoes.remove(id); }
        };
        service = new TransacaoService(transacaoRepo, categoriaRepo);

        salario = categoriaRepo.salvar(Categoria.nova("Salário", TipoTransacao.RECEITA));
        mercado = categoriaRepo.salvar(Categoria.nova("Mercado", TipoTransacao.DESPESA));
    }

    @Test
    void calculaResumoComReceitasEDespesas() {
        service.registrar(cmd("Salário", "3000.00", TipoTransacao.RECEITA, salario.getId()));
        service.registrar(cmd("Feira", "250.50", TipoTransacao.DESPESA, mercado.getId()));
        service.registrar(cmd("Padaria", "49.50", TipoTransacao.DESPESA, mercado.getId()));

        ResumoFinanceiro resumo = service.resumo();

        assertEquals(new BigDecimal("3000.00"), resumo.totalReceitas());
        assertEquals(new BigDecimal("300.00"), resumo.totalDespesas());
        assertEquals(new BigDecimal("2700.00"), resumo.saldo());
    }

    @Test
    void falhaQuandoCategoriaNaoExiste() {
        assertThrows(RecursoNaoEncontradoException.class,
                () -> service.registrar(cmd("X", "10", TipoTransacao.DESPESA, UUID.randomUUID())));
    }

    @Test
    void removeTransacaoExistente() {
        Transacao t = service.registrar(cmd("Feira", "100", TipoTransacao.DESPESA, mercado.getId()));
        service.remover(t.getId());
        assertTrue(service.listar().isEmpty());
    }

    private RegistrarTransacaoCommand cmd(String desc, String valor, TipoTransacao tipo, UUID categoriaId) {
        return new RegistrarTransacaoCommand(desc, new BigDecimal(valor), tipo, LocalDate.now(), categoriaId);
    }
}
