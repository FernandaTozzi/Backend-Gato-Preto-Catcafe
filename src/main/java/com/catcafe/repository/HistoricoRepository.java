package com.catcafe.repository;

import com.catcafe.model.Historico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoricoRepository extends JpaRepository<Historico, Long> {
}