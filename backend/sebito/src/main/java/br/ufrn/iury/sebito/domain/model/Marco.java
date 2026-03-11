package br.ufrn.iury.sebito.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "marcos")
@Getter
@Setter
@NoArgsConstructor
public class Marco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false)
    private Boolean obrigatorio;

    @Column(nullable = false)
    private Boolean concluido;

    @Column(name = "data_limite", nullable = false)
    private LocalDate dataLimite;

    @Column(name = "data_conclusao")
    private LocalDate dataConclusao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "projeto_id", nullable = false)
    private Projeto projeto;
}