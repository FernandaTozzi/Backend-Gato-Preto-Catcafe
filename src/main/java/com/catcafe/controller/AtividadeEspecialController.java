package com.catcafe.controller;

import com.catcafe.model.AtividadeEspecial;
import com.catcafe.service.AtividadeEspecialService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

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
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public AtividadeEspecial buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping(consumes = "multipart/form-data")
    public AtividadeEspecial criar(
            @RequestParam("titulo") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        AtividadeEspecial atividade = new AtividadeEspecial();
        atividade.setTitulo(titulo);
        atividade.setDescricao(descricao);

        if (imagem != null && !imagem.isEmpty()) {
            atividade.setImagem(salvarImagem(imagem));
        }

        return service.salvar(atividade);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public AtividadeEspecial atualizar(
            @PathVariable Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("descricao") String descricao,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        AtividadeEspecial atividade = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Atividade não encontrada"));

        atividade.setTitulo(titulo);
        atividade.setDescricao(descricao);

        if (imagem != null && !imagem.isEmpty()) {
            atividade.setImagem(salvarImagem(imagem));
        }

        return service.salvar(atividade);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    private String salvarImagem(MultipartFile imagem) throws IOException {
        String originalName = imagem.getOriginalFilename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get("uploads");
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(imagem.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}