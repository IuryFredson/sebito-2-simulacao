package br.ufrn.iury.sebito.service;

import br.ufrn.iury.sebito.domain.enums.StatusProjeto;
import br.ufrn.iury.sebito.domain.model.Projeto;
import br.ufrn.iury.sebito.domain.repository.ProjetoRepository;
import br.ufrn.iury.sebito.dto.projeto.ProjetoRequestDTO;
import br.ufrn.iury.sebito.dto.projeto.ProjetoResponseDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProjetoService {

    private final ProjetoRepository projetoRepository;

    public ProjetoService(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public ProjetoResponseDTO criar(ProjetoRequestDTO requestDTO) {
        validarDatas(requestDTO);

        Projeto projeto = new Projeto();
        projeto.setTitulo(requestDTO.titulo());
        projeto.setCoordenador(requestDTO.coordenador());
        projeto.setDataInicio(requestDTO.dataInicio());
        projeto.setDataFim(requestDTO.dataFim());
        projeto.setOrcamentoPrevisto(requestDTO.orcamentoPrevisto());
        projeto.setOrcamentoExecutado(BigDecimal.ZERO);
        projeto.setStatus(StatusProjeto.RASCUNHO);

        Projeto projetoSalvo = projetoRepository.save(projeto);

        return toResponseDTO(projetoSalvo);
    }

    public List<ProjetoResponseDTO> listarTodos() {
        return projetoRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private void validarDatas(ProjetoRequestDTO requestDTO) {
        if (requestDTO.dataFim().isBefore(requestDTO.dataInicio())) {
            throw new IllegalArgumentException("A data de fim não pode ser anterior à data de início");
        }
    }

    private ProjetoResponseDTO toResponseDTO(Projeto projeto) {
        return new ProjetoResponseDTO(
                projeto.getId(),
                projeto.getTitulo(),
                projeto.getCoordenador(),
                projeto.getDataInicio(),
                projeto.getDataFim(),
                projeto.getOrcamentoPrevisto(),
                projeto.getOrcamentoExecutado(),
                projeto.getStatus()
        );
    }
}