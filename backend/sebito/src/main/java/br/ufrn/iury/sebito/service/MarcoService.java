package br.ufrn.iury.sebito.service;

import br.ufrn.iury.sebito.domain.model.Marco;
import br.ufrn.iury.sebito.domain.model.Projeto;
import br.ufrn.iury.sebito.domain.repository.MarcoRepository;
import br.ufrn.iury.sebito.domain.repository.ProjetoRepository;
import br.ufrn.iury.sebito.dto.marco.MarcoRequestDTO;
import br.ufrn.iury.sebito.dto.marco.MarcoResponseDTO;
import br.ufrn.iury.sebito.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MarcoService {

    private final MarcoRepository marcoRepository;
    private final ProjetoRepository projetoRepository;

    public MarcoService(MarcoRepository marcoRepository, ProjetoRepository projetoRepository) {
        this.marcoRepository = marcoRepository;
        this.projetoRepository = projetoRepository;
    }

    public MarcoResponseDTO criar(Long projetoId, MarcoRequestDTO requestDTO) {
        Projeto projeto = projetoRepository.findById(projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Projeto não encontrado"));

        validarMarco(requestDTO);

        Marco marco = new Marco();
        marco.setNome(requestDTO.nome());
        marco.setObrigatorio(requestDTO.obrigatorio());
        marco.setConcluido(Boolean.TRUE.equals(requestDTO.concluido()));
        marco.setDataLimite(requestDTO.dataLimite());
        marco.setDataConclusao(requestDTO.dataConclusao());
        marco.setProjeto(projeto);

        Marco marcoSalvo = marcoRepository.save(marco);

        return toResponseDTO(marcoSalvo);
    }

    public List<MarcoResponseDTO> listarPorProjeto(Long projetoId) {
        if (!projetoRepository.existsById(projetoId)) {
            throw new ResourceNotFoundException("Projeto não encontrado");
        }

        return marcoRepository.findByProjetoId(projetoId)
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public MarcoResponseDTO concluir(Long projetoId, Long marcoId) {
        Marco marco = marcoRepository.findByIdAndProjetoId(marcoId, projetoId)
                .orElseThrow(() -> new ResourceNotFoundException("Marco não encontrado para o projeto informado"));

        marco.setConcluido(true);
        marco.setDataConclusao(LocalDate.now());

        Marco marcoAtualizado = marcoRepository.save(marco);

        return toResponseDTO(marcoAtualizado);
    }

    private void validarMarco(MarcoRequestDTO requestDTO) {
        if (Boolean.TRUE.equals(requestDTO.concluido()) && requestDTO.dataConclusao() == null) {
            throw new IllegalArgumentException("Um marco concluído deve possuir data de conclusão");
        }

        if (requestDTO.dataConclusao() != null && requestDTO.dataConclusao().isBefore(requestDTO.dataLimite().minusYears(50))) {
            throw new IllegalArgumentException("Data de conclusão inválida");
        }
    }

    private MarcoResponseDTO toResponseDTO(Marco marco) {
        return new MarcoResponseDTO(
                marco.getId(),
                marco.getNome(),
                marco.getObrigatorio(),
                marco.getConcluido(),
                marco.getDataLimite(),
                marco.getDataConclusao(),
                marco.getProjeto().getId()
        );
    }
}