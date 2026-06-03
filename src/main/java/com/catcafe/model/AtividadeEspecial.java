package com.catcafe.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "atividade_especial")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AtividadeEspecial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    private String imagem;

    @Column(name = "inicio_evento", nullable = false)
    private LocalDateTime inicioEvento;

    @Column(name = "fim_evento", nullable = false)
    private LocalDateTime fimEvento;
}