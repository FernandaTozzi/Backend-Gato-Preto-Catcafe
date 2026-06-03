package com.catcafe.service;

import com.catcafe.model.AtividadeEspecial;
import com.catcafe.repository.AtividadeEspecialRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AtividadeEspecialService {

    private final AtividadeEspecialRepository repository;

    public AtividadeEspecialService(
            AtividadeEspecialRepository repository
    ) {
        this.repository = repository;
    }

    public List<AtividadeEspecial> listarTodos() {
        return repository.findAll();
    }

    public Optional<AtividadeEspecial> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public AtividadeEspecial salvar(
            AtividadeEspecial atividade
    ) {
        return repository.save(atividade);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}