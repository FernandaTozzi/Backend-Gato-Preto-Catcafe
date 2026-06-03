package com.catcafe.controller;

import com.catcafe.model.AtividadeEspecial;
import com.catcafe.service.AtividadeEspecialService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/atividades")
@CrossOrigin("*")
public class AtividadeEspecialController {

    private final AtividadeEspecialService service;

    public AtividadeEspecialController(AtividadeEspecialService service) {
        this.service = service;
    }

    @GetMapping
    public List<AtividadeEspecial> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public AtividadeEspecial buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping(consumes = "multipart/form-data")
    public AtividadeEspecial criar(
            @RequestParam("titulo") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam("inicioEvento") String inicioEvento,
            @RequestParam("fimEvento") String fimEvento,
            @RequestParam("imagem") MultipartFile imagem
    ) throws IOException {

        String fileName = imagem.getOriginalFilename();

        Path path = Paths.get("uploads/" + fileName);

        Files.createDirectories(path.getParent());

        Files.write(path, imagem.getBytes());

        AtividadeEspecial atividade = new AtividadeEspecial();

        atividade.setTitulo(titulo);
        atividade.setDescricao(descricao);
        atividade.setImagem(fileName);
        atividade.setInicioEvento(LocalDateTime.parse(inicioEvento));
        atividade.setFimEvento(LocalDateTime.parse(fimEvento));

        return service.salvar(atividade);
    }

    @PutMapping("/{id}")
    public AtividadeEspecial atualizar(
            @PathVariable Long id,
            @RequestBody AtividadeEspecial atividade
    ) {
        atividade.setId(id);
        return service.salvar(atividade);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}