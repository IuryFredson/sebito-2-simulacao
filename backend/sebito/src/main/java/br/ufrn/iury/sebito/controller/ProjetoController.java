package br.ufrn.iury.sebito.controller;

import br.ufrn.iury.sebito.dto.projeto.ProjetoRequestDTO;
import br.ufrn.iury.sebito.dto.projeto.ProjetoResponseDTO;
import br.ufrn.iury.sebito.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projetos")
public class ProjetoController {

    private final ProjetoService projetoService;

    public ProjetoController(ProjetoService projetoService) {
        this.projetoService = projetoService;
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
}