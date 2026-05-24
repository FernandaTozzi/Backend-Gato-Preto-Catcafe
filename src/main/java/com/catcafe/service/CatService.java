package com.catcafe.service;

import com.catcafe.model.Cat;
import com.catcafe.repository.CatRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatService {

    private final CatRepository repository;

    public CatService(CatRepository repository) {
        this.repository = repository;
    }

    public List<Cat> listarTodos() {
        return repository.findAll();
    }

    public Optional<Cat> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Cat salvar(Cat cat) {
        return repository.save(cat);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}