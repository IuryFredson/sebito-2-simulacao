package br.ufrn.iury.sebito.dto.marco;

import java.time.LocalDate;

public record MarcoResponseDTO(
        Long id,
        String nome,
        Boolean obrigatorio,
        Boolean concluido,
        LocalDate dataLimite,
        LocalDate dataConclusao,
        Long projetoId
) {
}