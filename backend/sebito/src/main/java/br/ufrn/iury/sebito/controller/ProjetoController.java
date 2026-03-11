package br.ufrn.iury.sebito.controller;

import br.ufrn.iury.sebito.dto.marco.MarcoRequestDTO;
import br.ufrn.iury.sebito.dto.marco.MarcoResponseDTO;
import br.ufrn.iury.sebito.dto.projeto.AlterarStatusProjetoRequestDTO;
import br.ufrn.iury.sebito.dto.projeto.ProjetoRequestDTO;
import br.ufrn.iury.sebito.dto.projeto.ProjetoResponseDTO;
import br.ufrn.iury.sebito.service.MarcoService;
import br.ufrn.iury.sebito.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;
    private final MarcoService marcoService;

    public ProjetoController(ProjetoService projetoService, MarcoService marcoService) {
        this.projetoService = projetoService;
        this.marcoService = marcoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjetoResponseDTO criar(@RequestBody @Valid ProjetoRequestDTO requestDTO) {
        return projetoService.criar(requestDTO);
    }

    @GetMapping
    public List<ProjetoResponseDTO> listarTodos() {
        return projetoService.listarTodos();
    }

    @PatchMapping("/{projetoId}/status")
    public ProjetoResponseDTO alterarStatus(
            @PathVariable Long projetoId,
            @RequestBody @Valid AlterarStatusProjetoRequestDTO requestDTO
    ) {
        return projetoService.alterarStatus(projetoId, requestDTO);
    }

    @PostMapping("/{projetoId}/marcos")
    @ResponseStatus(HttpStatus.CREATED)
    public MarcoResponseDTO criarMarco(
            @PathVariable Long projetoId,
            @RequestBody @Valid MarcoRequestDTO requestDTO
    ) {
        return marcoService.criar(projetoId, requestDTO);
    }

    @GetMapping("/{projetoId}/marcos")
    public List<MarcoResponseDTO> listarMarcos(@PathVariable Long projetoId) {
        return marcoService.listarPorProjeto(projetoId);
    }

    @PatchMapping("/{projetoId}/marcos/{marcoId}/concluir")
    public MarcoResponseDTO concluirMarco(
            @PathVariable Long projetoId,
            @PathVariable Long marcoId
    ) {
        return marcoService.concluir(projetoId, marcoId);
    }
}