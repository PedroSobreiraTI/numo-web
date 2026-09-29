package com.pedrosobreira.numo.adapter.in.web;

import com.pedrosobreira.numo.adapter.in.web.dto.TransacaoRequest;
import com.pedrosobreira.numo.adapter.in.web.dto.TransacaoResponse;
import com.pedrosobreira.numo.application.port.in.TransacaoUseCase;
import com.pedrosobreira.numo.application.port.in.TransacaoUseCase.RegistrarTransacaoCommand;
import com.pedrosobreira.numo.domain.model.ResumoFinanceiro;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoUseCase useCase;

    public TransacaoController(TransacaoUseCase useCase) {
        this.useCase = useCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransacaoResponse registrar(@Valid @RequestBody TransacaoRequest r) {
        return TransacaoResponse.from(useCase.registrar(new RegistrarTransacaoCommand(
                r.descricao(), r.valor(), r.tipo(), r.data(), r.categoriaId())));
    }

    @GetMapping
    public List<TransacaoResponse> listar() {
        return useCase.listar().stream().map(TransacaoResponse::from).toList();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable UUID id) {
        useCase.remover(id);
    }

    @GetMapping("/resumo")
    public ResumoFinanceiro resumo() {
        return useCase.resumo();
    }
}
