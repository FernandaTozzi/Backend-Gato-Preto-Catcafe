package com.catcafe.controller;

import com.catcafe.model.ItemCardapio;
import com.catcafe.service.ItemCardapioService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cardapio")
@CrossOrigin("*")
public class ItemCardapioController {

    private final ItemCardapioService service;

    public ItemCardapioController(ItemCardapioService service) {
        this.service = service;
    }

    @GetMapping
    public List<ItemCardapio> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public ItemCardapio buscar(@PathVariable Long id) {
        return service.buscarPorId(id).orElse(null);
    }

    @PostMapping(consumes = "multipart/form-data")
    public ItemCardapio criar(
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("preco") BigDecimal preco,
            @RequestParam("categoria") String categoria,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        ItemCardapio item = new ItemCardapio();

        item.setNome(nome);
        item.setDescricao(descricao);
        item.setPreco(preco);
        item.setCategoria(categoria);

        if (imagem != null && !imagem.isEmpty()) {
            item.setImagem(salvarImagem(imagem));
        }

        return service.salvar(item);
    }

    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ItemCardapio atualizar(
            @PathVariable Long id,
            @RequestParam("nome") String nome,
            @RequestParam("descricao") String descricao,
            @RequestParam("preco") BigDecimal preco,
            @RequestParam("categoria") String categoria,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) throws IOException {

        ItemCardapio item = service.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));

        item.setNome(nome);
        item.setDescricao(descricao);
        item.setPreco(preco);
        item.setCategoria(categoria);

        if (imagem != null && !imagem.isEmpty()) {
            item.setImagem(salvarImagem(imagem));
        }

        return service.salvar(item);
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

        Files.copy(
                imagem.getInputStream(),
                filePath,
                StandardCopyOption.REPLACE_EXISTING
        );

        return fileName;
    }
}
