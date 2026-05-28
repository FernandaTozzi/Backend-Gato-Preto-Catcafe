package com.catcafe.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(
            mappedBy = "atividadeEspecial",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @JsonManagedReference
    private List<Historico> historico = new ArrayList<>();
}