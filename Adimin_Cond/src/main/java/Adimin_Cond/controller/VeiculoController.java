package Adimin_Cond.controller;

import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.dto.veiculo.VeiculoRequestDTO;
import Adimin_Cond.dto.veiculo.VeiculoResponseDTO;
import Adimin_Cond.dto.veiculo.VeiculoUpdateRequestDTO;
import Adimin_Cond.service.VeiculoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculo")
@RequiredArgsConstructor
@Tag(name = "Veículo")
public class VeiculoController {

    private final VeiculoService veiculoService;

    @PostMapping("/adicionar")
    public ResponseEntity<VeiculoResponseDTO> criar(@Valid @RequestBody VeiculoRequestDTO dto) {
        VeiculoResponseDTO response = veiculoService.salvarVeiculo(dto);
        return ResponseEntity.status(201).body(response);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<VeiculoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody VeiculoUpdateRequestDTO dto) {
        VeiculoResponseDTO response = veiculoService.atualizarVeiculo(id, dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar-placa/{placa}")
    public ResponseEntity<VeiculoResponseDTO> buscarPorPlaca(@PathVariable String placa) {
        VeiculoResponseDTO response = veiculoService.buscarPlaca(placa);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<VeiculoResponseDTO>> listarTodos() {
        List<VeiculoResponseDTO> response = veiculoService.listarTodos();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<VeiculoResponseDTO> buscarPorId(@PathVariable Long id) {
        VeiculoResponseDTO response = veiculoService.veiculoID(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listar-por-status/{status}")
    public ResponseEntity<List<VeiculoResponseDTO>> listarPorStatus(@PathVariable StatusVeiculo status) {
        List<VeiculoResponseDTO> response = veiculoService.listarPorStatus(status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/desativar/{id}")
    public ResponseEntity<VeiculoResponseDTO> desativarVeiculo(@PathVariable Long id) {
        VeiculoResponseDTO veiculo = veiculoService.desativarVeiculo(id);
        return ResponseEntity.ok(veiculo);
    }
}
