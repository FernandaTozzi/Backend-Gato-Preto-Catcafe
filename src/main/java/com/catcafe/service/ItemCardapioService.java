package com.catcafe.service;

import com.catcafe.model.ItemCardapio;
import com.catcafe.repository.ItemCardapioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCardapioService {

    private final ItemCardapioRepository repository;

    public ItemCardapioService(ItemCardapioRepository repository) {
        this.repository = repository;
    }

    public List<ItemCardapio> listarTodos() {
        return repository.findAll();
    }

    public Optional<ItemCardapio> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public ItemCardapio salvar(ItemCardapio item) {
        return repository.save(item);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}