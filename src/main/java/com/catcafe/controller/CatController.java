package com.catcafe.controller;

import com.catcafe.model.Cat;
import com.catcafe.service.CatService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cats")
@CrossOrigin("*")
public class CatController {

    private final CatService service;

    public CatController(CatService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cat> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Cat buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping(consumes = "multipart/form-data")
    public Cat criar(
            @RequestParam("nome") String nome,
            @RequestParam("idade") Integer idade,
            @RequestParam("genero") String genero,
            @RequestParam("tipoAdocao") String tipoAdocao,
            @RequestParam("descricao") String descricao,
            @RequestParam("status") String status,
            @RequestParam("foto") MultipartFile foto
    ) throws IOException {

        String fileName = salvarImagem(foto);

        Cat cat = new Cat();
        cat.setNome(nome);
        cat.setIdade(idade);
        cat.setGenero(genero);
        cat.setTipoAdocao(tipoAdocao);
        cat.setDescricao(descricao);
        cat.setStatus(status);
        cat.setFoto(fileName);

        return service.salvar(cat);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public Cat atualizar(
            @PathVariable Long id,
            @RequestParam("nome") String nome,
            @RequestParam("idade") Integer idade,
            @RequestParam("genero") String genero,
            @RequestParam("tipoAdocao") String tipoAdocao,
            @RequestParam("descricao") String descricao,
            @RequestParam("status") String status,
            @RequestParam(value = "foto", required = false) MultipartFile foto
    ) throws IOException {

        Cat catExistente = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Gato não encontrado"));

        catExistente.setNome(nome);
        catExistente.setIdade(idade);
        catExistente.setGenero(genero);
        catExistente.setTipoAdocao(tipoAdocao);
        catExistente.setDescricao(descricao);
        catExistente.setStatus(status);

        if (foto != null && !foto.isEmpty()) {
            String fileName = salvarImagem(foto);
            catExistente.setFoto(fileName);
        }

        return service.salvar(catExistente);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }

    private String salvarImagem(MultipartFile foto) throws IOException {
        String originalName = foto.getOriginalFilename();
        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(originalName.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID() + extension;

        Path uploadPath = Paths.get("uploads");
        Files.createDirectories(uploadPath);

        Path filePath = uploadPath.resolve(fileName);
        Files.copy(foto.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }
}