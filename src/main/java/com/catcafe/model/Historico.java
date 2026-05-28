package com.catcafe.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "historico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Historico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataEvento;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;

    @ManyToOne
    @JoinColumn(name = "atividade_id", nullable = false)
    @JsonBackReference
    private AtividadeEspecial atividadeEspecial;
}