package br.ufrn.iury.sebito.domain.repository;

import br.ufrn.iury.sebito.domain.model.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
}