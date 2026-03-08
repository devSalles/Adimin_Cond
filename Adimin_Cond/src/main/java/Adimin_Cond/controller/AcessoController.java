package Adimin_Cond.controller;

import Adimin_Cond.Enum.TipoAcesso;
import Adimin_Cond.dto.acesso.AcessoEntradaRequestDTO;
import Adimin_Cond.dto.acesso.AcessoResponseDTO;
import Adimin_Cond.dto.acesso.AcessoSaidaRequestDTO;
import Adimin_Cond.service.AcessoService;
import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/acesso")
@Tag(name = "Acesso")
public class AcessoController {

    @Autowired
    private AcessoService acessoService;

    @PostMapping("/entrada")
    public ResponseEntity<AcessoResponseDTO> registrarEntrada(@Valid @RequestBody AcessoEntradaRequestDTO acessoDTO) {
        AcessoResponseDTO response = acessoService.registarEntrada(acessoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/saida")
    public ResponseEntity<AcessoResponseDTO> registrarSaida(@Valid @RequestBody AcessoSaidaRequestDTO acessoDTO) {
        AcessoResponseDTO response = acessoService.registrarSaida(acessoDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/listagem")
    public ResponseEntity<List<AcessoResponseDTO>> listarTodos() {
        List<AcessoResponseDTO> acessos = acessoService.listarTodos();
        return ResponseEntity.ok(acessos);
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<?> buscarId(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.acessoService.buscarID(id));
    }

    @GetMapping("/consultar-por-data-entrada")
    public ResponseEntity<?> consultarDataEntrada(
            @RequestParam("incio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)LocalDate inicio,
            @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fim
    )
    {
        return ResponseEntity.ok(this.acessoService.consultarDataHoraEntrada(inicio,fim));
    }

    @GetMapping("/consultar-por-data-saida")
    public ResponseEntity<?> consultarDataSaida(
                    @RequestParam("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate incio,
                    @RequestParam("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate fim
    )
    {
        return ResponseEntity.ok(this.acessoService.consultarDataHoraSaida(incio,fim));
    }

    @GetMapping("/consultar-por-status")
    public ResponseEntity<?> consultarPorStatus(@RequestParam TipoAcesso tipoAcesso)
    {
        return ResponseEntity.ok(this.acessoService.consultarTiposAcessos(tipoAcesso));
    }

    @GetMapping("/consultar-id-veiculo/{idVeiculo}")
    public ResponseEntity<List<AcessoResponseDTO>> consultarVeiculoID(@PathVariable Long idVeiculo)
    {
        List<AcessoResponseDTO>acessoResponseDTOList = this.acessoService.consultarPorVeiculo(idVeiculo);
        return ResponseEntity.ok(acessoResponseDTOList);
    }
}
