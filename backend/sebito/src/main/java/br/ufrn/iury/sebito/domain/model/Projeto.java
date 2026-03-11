package br.ufrn.iury.sebito.domain.model;

import br.ufrn.iury.sebito.domain.enums.StatusProjeto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "projetos")
@Getter
@Setter
@NoArgsConstructor
public class Projeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titulo;

    @Column(nullable = false, length = 150)
    private String coordenador;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "orcamento_previsto", nullable = false, precision = 14, scale = 2)
    private BigDecimal orcamentoPrevisto;

    @Column(name = "orcamento_executado", nullable = false, precision = 14, scale = 2)
    private BigDecimal orcamentoExecutado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 40)
    private StatusProjeto status;
}