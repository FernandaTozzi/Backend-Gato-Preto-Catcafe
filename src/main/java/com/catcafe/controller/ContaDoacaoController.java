package com.catcafe.controller;

import com.catcafe.model.ContaDoacao;
import com.catcafe.service.ContaDoacaoService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doacao")
@CrossOrigin("*")
public class ContaDoacaoController {

    private final ContaDoacaoService service;

    public ContaDoacaoController(ContaDoacaoService service) {
        this.service = service;
    }

    @GetMapping
    public ContaDoacao visualizarConta() {
        return service.buscarConta();
    }

    @PutMapping
    public ContaDoacao atualizarConta(
            @RequestBody ContaDoacao conta
    ) {
        return service.atualizar(conta);
    }
}