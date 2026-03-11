package br.ufrn.iury.sebito.dto.marco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MarcoRequestDTO(

        @NotBlank(message = "O nome do marco é obrigatório")
        String nome,

        @NotNull(message = "O campo obrigatorio é obrigatório")
        Boolean obrigatorio,

        @NotNull(message = "A data limite é obrigatória")
        LocalDate dataLimite,

        Boolean concluido,

        LocalDate dataConclusao
) {
}