package br.ufrn.iury.sebito.dto.projeto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ProjetoRequestDTO(

        @NotBlank(message = "O título é obrigatório")
        String titulo,

        @NotBlank(message = "O coordenador é obrigatório")
        String coordenador,

        @NotNull(message = "A data de início é obrigatória")
        LocalDate dataInicio,

        @NotNull(message = "A data de fim é obrigatória")
        LocalDate dataFim,

        @NotNull(message = "O orçamento previsto é obrigatório")
        @DecimalMin(value = "0.01", message = "O orçamento previsto deve ser maior que zero")
        BigDecimal orcamentoPrevisto
) {
}