package Adimin_Cond.controller;

import Adimin_Cond.dto.taxaCond.TaxaCondRequestDTO;
import Adimin_Cond.dto.taxaCond.TaxaCondResponseDTO;
import Adimin_Cond.service.TaxaCondominioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/taxas")
@RequiredArgsConstructor
@Tag(name = "Taxa Condomínio")
public class TaxaCondominioController {

    private final TaxaCondominioService taxaCondominioService;

    @PostMapping("/adicionar-taxa")
    public ResponseEntity<TaxaCondResponseDTO> gerarTaxa(@RequestBody @Valid TaxaCondRequestDTO taxaDTO) {
        TaxaCondResponseDTO response = taxaCondominioService.gerarTaxa(taxaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/pagar-taxa/{id}")
    public ResponseEntity<TaxaCondResponseDTO> pagarTaxa(@PathVariable Long id,
                                                         @RequestParam("dataPagamento") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataPagamento) {
        TaxaCondResponseDTO response = taxaCondominioService.pagarTaxa(id,dataPagamento);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<TaxaCondResponseDTO> buscarPorId(@PathVariable Long id) {
        TaxaCondResponseDTO response = taxaCondominioService.buscarID(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<TaxaCondResponseDTO>> listarTodas() {
        List<TaxaCondResponseDTO> response = taxaCondominioService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data-pagamento")
    public ResponseEntity<List<TaxaCondResponseDTO>> buscarPorDataPagamento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        List<TaxaCondResponseDTO> response = taxaCondominioService.buscarPorDataDePagamento(inicio, fim);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data-vencimento")
    public ResponseEntity<List<TaxaCondResponseDTO>> buscarPorDataVencimento(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim) {
        List<TaxaCondResponseDTO> response = taxaCondominioService.buscarPorDataDeVencimento(inicio, fim);
        return ResponseEntity.ok(response);
    }
}