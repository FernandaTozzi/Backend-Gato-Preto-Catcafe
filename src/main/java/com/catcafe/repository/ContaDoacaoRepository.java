package com.catcafe.repository;

import com.catcafe.model.ContaDoacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaDoacaoRepository extends JpaRepository<ContaDoacao, Long> {
}