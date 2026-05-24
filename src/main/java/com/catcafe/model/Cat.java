package com.catcafe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private Integer idade;
    private String genero;
    private String tipoAdocao;
    private String descricao;
    private String status;
    private String foto;
}