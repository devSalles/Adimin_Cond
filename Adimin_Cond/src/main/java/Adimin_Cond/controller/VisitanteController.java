package Adimin_Cond.controller;

import Adimin_Cond.dto.visitante.VisitanteResponseDTO;
import Adimin_Cond.dto.visitante.VisitanteSaidaRequestDTO;
import Adimin_Cond.dto.visitante.VistanteEntradaRequestDTO;
import Adimin_Cond.service.VisitanteService;
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
@RequestMapping("/visitante")
@RequiredArgsConstructor
@Tag(name = "Visitante")
public class VisitanteController {

    private final VisitanteService visitanteService;

    @PostMapping("/entrada")
    public ResponseEntity<VisitanteResponseDTO> registrarEntrada(@RequestBody @Valid VistanteEntradaRequestDTO request)
    {
        VisitanteResponseDTO response = visitanteService.registrarVisita(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/saida")
    public ResponseEntity<VisitanteResponseDTO> registrarSaida(@RequestBody @Valid VisitanteSaidaRequestDTO request)
    {
        VisitanteResponseDTO response = visitanteService.registrarSaida(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar-registros")
    public ResponseEntity<List<VisitanteResponseDTO>> listarTodos()
    {
        List<VisitanteResponseDTO> responseDTO = this.visitanteService.listarTodas();
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/buscar-cpf/{cpf}")
    public ResponseEntity<VisitanteResponseDTO> buscarCPF(@RequestParam String cpf)
    {
        VisitanteResponseDTO response = this.visitanteService.buscarPorCPF(cpf);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar-nome/{nome}")
    public ResponseEntity<VisitanteResponseDTO> buscarNome(@RequestParam String nome)
    {
        VisitanteResponseDTO response = this.visitanteService.buscarPorNome(nome);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<VisitanteResponseDTO> buscarId(@PathVariable Long id)
    {
        VisitanteResponseDTO response = this.visitanteService.buscarID(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/pesquisar-por-data-entrada")
    public ResponseEntity<?> buscarDataEntrada(
            @RequestParam("inicio") @DateTimeFormat(iso =DateTimeFormat.ISO.DATE_TIME) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fim )
    {
        return ResponseEntity.ok(this.visitanteService.pesquisarPeriodoDataEntrada(inicio,fim));
    }

    @GetMapping("/pesquisar-por-data-saida")
    public ResponseEntity<?> buscarDataSaida(
            @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fim
    ){
        return ResponseEntity.ok(this.visitanteService.pesquisarPeriodoDataSaida(inicio,fim));
    }
}
