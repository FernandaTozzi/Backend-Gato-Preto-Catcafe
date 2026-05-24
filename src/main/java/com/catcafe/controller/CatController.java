package com.catcafe.controller;

import com.catcafe.model.Cat;
import com.catcafe.service.CatService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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
            @RequestParam("idade") int idade,
            @RequestParam("genero") String genero,
            @RequestParam("tipoAdocao") String tipoAdocao,
            @RequestParam("descricao") String descricao,
            @RequestParam("status") String status,
            @RequestParam("foto") MultipartFile foto
    ) throws IOException {

        // 📁 nome do arquivo
        String fileName = foto.getOriginalFilename();

        // 📁 caminho onde será salvo
        Path path = Paths.get("uploads/" + fileName);

        // 📁 cria pasta se não existir
        Files.createDirectories(path.getParent());

        // 💾 salva arquivo
        Files.write(path, foto.getBytes());

        // 🐱 cria objeto
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
    @PutMapping("/{id}")
    public Cat atualizar(@PathVariable Long id, @RequestBody Cat cat) {
        cat.setId(id);
        return service.salvar(cat);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}