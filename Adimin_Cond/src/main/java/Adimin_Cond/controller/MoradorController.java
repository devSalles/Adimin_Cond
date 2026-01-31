package Adimin_Cond.controller;

import Adimin_Cond.dto.morador.MoradorUpdateRequestDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Adimin_Cond.dto.morador.MoradorRequestDTO;
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

    @PostMapping("/adicionar")
    public ResponseEntity<?> salvar(@Valid @RequestBody MoradorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moradorService.salvar(dto));
    }

    @PostMapping("/morador/{moradorId}/veiculos/{veiculoId}")
    public ResponseEntity<?> adicionarVeiculo(@PathVariable Long moradorId, @PathVariable Long veiculoId) {
        return ResponseEntity.ok(moradorService.adicionarVeiculo(moradorId, veiculoId));
    }

    @PostMapping("/morador/{moradorId}/visitantes/{visitanteId}")
    public ResponseEntity<?> adicionarVisitante(@PathVariable Long moradorId, @PathVariable Long visitanteId) {
        return ResponseEntity.ok( moradorService.adicionarVisitante(moradorId, visitanteId));
    }

    @PostMapping("/morador/{moradorId}/taxas/{taxaId}")
    public ResponseEntity<?> adicionarTaxaCondominio(@PathVariable Long moradorId, @PathVariable Long taxaId) {
        return ResponseEntity.ok( moradorService.adicionarTaxaCondominio(moradorId, taxaId));
    }

    @PutMapping("/atualizar-morador/{id}")
    public  ResponseEntity<?> atualizarMorador(@PathVariable Long id, @Valid @RequestBody MoradorUpdateRequestDTO dto)
    {
        return ResponseEntity.ok(this.moradorService.atualizarMorador(id,dto));
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<?> listarTodos()
    {
        return ResponseEntity.ok(this.moradorService.listarTodos());
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<?> buscarID(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.moradorService.buscarID(id));
    }

    @GetMapping("/buscar-nome/{nome}")
    public ResponseEntity<?> buscarNome(@PathVariable String nome)
    {
        return ResponseEntity.ok(this.moradorService.buscarNomeMorador(nome));
    }

    @GetMapping("/buscar-CPF/{cpf}")
    public ResponseEntity<?> buscarCpf(@PathVariable String cpf)
    {
        return ResponseEntity.ok(this.moradorService.buscarCPFMorador(cpf));
    }

    @GetMapping("/buscar-email/{email}")
    public ResponseEntity<?> buscarID(@PathVariable String email)
    {
        return ResponseEntity.ok(this.moradorService.buscarEmailMorador(email));
    }

    @DeleteMapping("/deletar-morador/{id}")
    public ResponseEntity<?> deletarMorador(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.moradorService.desativarMorador(id));
    }

}
