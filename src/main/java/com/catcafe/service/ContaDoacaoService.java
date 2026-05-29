package com.catcafe.service;

import com.catcafe.model.ContaDoacao;
import com.catcafe.repository.ContaDoacaoRepository;
import org.springframework.stereotype.Service;

@Service
public class ContaDoacaoService {

    private final ContaDoacaoRepository repository;

    public ContaDoacaoService(ContaDoacaoRepository repository) {
        this.repository = repository;
    }

    public ContaDoacao buscarConta() {
        return repository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Conta não encontrada"));
    }

    public ContaDoacao atualizar(ContaDoacao contaAtualizada) {

        ContaDoacao conta = buscarConta();

        conta.setBanco(contaAtualizada.getBanco());
        conta.setAgencia(contaAtualizada.getAgencia());
        conta.setConta(contaAtualizada.getConta());
        conta.setChavePix(contaAtualizada.getChavePix());
        conta.setTitular(contaAtualizada.getTitular());

        return repository.save(conta);
    }
}