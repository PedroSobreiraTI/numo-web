package com.pedrosobreira.numo.application.port.in;

import com.pedrosobreira.numo.domain.model.Categoria;
import com.pedrosobreira.numo.domain.model.TipoTransacao;

import java.util.List;

/** Porta de entrada: o que o mundo externo pode pedir sobre categorias. */
public interface CategoriaUseCase {

    Categoria criar(CriarCategoriaCommand command);

    List<Categoria> listar();

    record CriarCategoriaCommand(String nome, TipoTransacao tipo) {}
}
