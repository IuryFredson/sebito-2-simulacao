package br.ufrn.iury.sebito.domain.repository;

import br.ufrn.iury.sebito.domain.model.Marco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MarcoRepository extends JpaRepository<Marco, Long> {

    List<Marco> findByProjetoId(Long projetoId);

    Optional<Marco> findByIdAndProjetoId(Long id, Long projetoId);

    long countByProjetoIdAndObrigatorioTrueAndConcluidoFalse(Long projetoId);
}