package com.catcafe.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "conta_doacao")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaDoacao {

    @Id
    private Long id;

    private String banco;

    private String agencia;

    private String conta;

    private String chavePix;

    private String titular;
}