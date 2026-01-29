package Adimin_Cond.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Adimin_Cond.dto.morador.MoradorRequestDTO;
import Adimin_Cond.dto.morador.MoradorResponseDTO;
import Adimin_Cond.service.MoradorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/moradores")
@RequiredArgsConstructor
@Tag(name = "Moradores")
public class MoradorController {

    private final MoradorService moradorService;

    @PostMapping
    public ResponseEntity<MoradorResponseDTO> salvar(@Valid @RequestBody MoradorRequestDTO dto) {
        MoradorResponseDTO response = moradorService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{moradorId}/apartamento/{apartamentoId}")
    public ResponseEntity<MoradorResponseDTO> vincularApartamento(
            @PathVariable Long moradorId,
            @PathVariable Long apartamentoId) {
        MoradorResponseDTO response = moradorService.vincularApartamento(moradorId, apartamentoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{moradorId}/veiculos/{veiculoId}")
    public ResponseEntity<MoradorResponseDTO> adicionarVeiculo(
            @PathVariable Long moradorId,
            @PathVariable Long veiculoId) {
        MoradorResponseDTO response = moradorService.adicionarVeiculo(moradorId, veiculoId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{moradorId}/visitantes/{visitanteId}")
    public ResponseEntity<MoradorResponseDTO> adicionarVisitante(
            @PathVariable Long moradorId,
            @PathVariable Long visitanteId) {
        MoradorResponseDTO response = moradorService.adicionarVisitante(moradorId, visitanteId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{moradorId}/taxas/{taxaId}")
    public ResponseEntity<MoradorResponseDTO> adicionarTaxaCondominio(
            @PathVariable Long moradorId,
            @PathVariable Long taxaId) {
        MoradorResponseDTO response = moradorService.adicionarTaxaCondominio(moradorId, taxaId);
        return ResponseEntity.ok(response);
    }
}
