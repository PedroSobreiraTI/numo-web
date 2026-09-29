package com.pedrosobreira.numo.domain;

import com.pedrosobreira.numo.domain.exception.RegraDeNegocioException;
import com.pedrosobreira.numo.domain.model.Categoria;
import com.pedrosobreira.numo.domain.model.TipoTransacao;
import com.pedrosobreira.numo.domain.model.Transacao;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/** Testes puros de domínio: sem Spring, sem banco, rodam em milissegundos. */
class TransacaoTest {

    private final Categoria mercado = Categoria.nova("Mercado", TipoTransacao.DESPESA);

    @Test
    void criaDespesaValida() {
        Transacao t = Transacao.nova("Compra do mês", new BigDecimal("350.90"),
                TipoTransacao.DESPESA, LocalDate.now(), mercado);

        assertEquals(new BigDecimal("-350.90"), t.valorComSinal());
        assertEquals(mercado.getId(), t.getCategoriaId());
    }

    @Test
    void rejeitaValorZeroOuNegativo() {
        assertThrows(RegraDeNegocioException.class, () -> Transacao.nova("X", BigDecimal.ZERO,
                TipoTransacao.DESPESA, LocalDate.now(), mercado));
        assertThrows(RegraDeNegocioException.class, () -> Transacao.nova("X", new BigDecimal("-10"),
                TipoTransacao.DESPESA, LocalDate.now(), mercado));
    }

    @Test
    void rejeitaTipoIncompativelComCategoria() {
        RegraDeNegocioException ex = assertThrows(RegraDeNegocioException.class,
                () -> Transacao.nova("Salário", new BigDecimal("3000"),
                        TipoTransacao.RECEITA, LocalDate.now(), mercado));
        assertTrue(ex.getMessage().contains("Mercado"));
    }

    @Test
    void rejeitaDescricaoEmBranco() {
        assertThrows(RegraDeNegocioException.class, () -> Transacao.nova("  ", BigDecimal.TEN,
                TipoTransacao.DESPESA, LocalDate.now(), mercado));
    }
}
