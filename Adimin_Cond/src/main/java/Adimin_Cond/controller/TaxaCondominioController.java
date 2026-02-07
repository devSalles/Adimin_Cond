package Adimin_Cond.controller;

import Adimin_Cond.dto.taxaCond.TaxaCondRequestDTO;
import Adimin_Cond.dto.taxaCond.TaxaCondResponseDTO;
import Adimin_Cond.service.TaxaCondominioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/taxas")
@RequiredArgsConstructor
public class TaxaCondominioController {

    private final TaxaCondominioService taxaCondominioService;

    @PostMapping
    public ResponseEntity<TaxaCondResponseDTO> gerarTaxa(@RequestBody @Valid TaxaCondRequestDTO taxaDTO) {
        TaxaCondResponseDTO response = taxaCondominioService.gerarTaxa(taxaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/pagar/{id}")
    public ResponseEntity<TaxaCondResponseDTO> pagarTaxa(@PathVariable Long id) {
        TaxaCondResponseDTO response = taxaCondominioService.pagarTaxa(id);
        return ResponseEntity.ok(response);
    }
}