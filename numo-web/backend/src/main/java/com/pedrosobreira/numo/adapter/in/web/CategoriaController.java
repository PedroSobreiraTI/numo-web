package com.pedrosobreira.numo.adapter.in.web;

import com.pedrosobreira.numo.adapter.in.web.dto.CategoriaRequest;
import com.pedrosobreira.numo.adapter.in.web.dto.CategoriaResponse;
import com.pedrosobreira.numo.application.port.in.CategoriaUseCase;
import com.pedrosobreira.numo.application.port.in.CategoriaUseCase.CriarCategoriaCommand;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** Adapter de entrada: recebe HTTP e chama a porta de entrada. Não tem regra de negócio. */
@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    private final CategoriaUseCase useCase;

    public CategoriaController(CategoriaUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoriaResponse criar(@Valid @RequestBody CategoriaRequest request) {
        return CategoriaResponse.from(useCase.criar(new CriarCategoriaCommand(request.nome(), request.tipo())));
    }

    @GetMapping
    public List<CategoriaResponse> listar() {
        return useCase.listar().stream().map(CategoriaResponse::from).toList();
    }
}
