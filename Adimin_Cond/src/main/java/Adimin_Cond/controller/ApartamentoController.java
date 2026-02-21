package Adimin_Cond.controller;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.dto.apartamento.ApartamentoRequestDTO;
import Adimin_Cond.service.ApartamentoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/apartamento")
@RequiredArgsConstructor
@Tag(name = "Apartamento")
public class ApartamentoController {

    private final ApartamentoService apartamentoService;

    @PostMapping("/salvar-apartamento")
    public ResponseEntity<?> salvar(@RequestBody @Valid ApartamentoRequestDTO dto)
    {
        return ResponseEntity.ok(this.apartamentoService.salvar(dto));
    }

    @PostMapping("/vinvular-apartamento/{idApartamento}/{idMorador}")
    public ResponseEntity<?> vincular(@PathVariable Long idApartamento ,@PathVariable Long idMorador)
    {
        return ResponseEntity.ok(this.apartamentoService.vincularApartamento(idApartamento,idMorador));
    }

    @PostMapping("/desvincular-apartamento/{id}")
    public ResponseEntity<?> desvincularApartamento(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.apartamentoService.desvincularApartamento(id));
    }

    @PutMapping("/atualizarApto-id/{id}")
    public ResponseEntity<?> atualizarApto(@PathVariable Long id, @Valid @RequestBody ApartamentoRequestDTO dto)
    {
        return ResponseEntity.ok(this.apartamentoService.atualizarApartamento(id,dto));
    }

    @PutMapping("/colocar-manutencao/{id}")
    public ResponseEntity<?> colocarManutencao(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.apartamentoService.colocarEmManutencao(id));
    }

    @PutMapping("/retirar-manutencao/{id}")
    public ResponseEntity<?>retirarManutencao(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.apartamentoService.retirarDaManutencao(id));
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<?> listarTodos()
    {
        return ResponseEntity.ok(this.apartamentoService.listarTodos());
    }

    @GetMapping("/buscar-id/{id}")
    public ResponseEntity<?> buscarID(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.apartamentoService.buscarID(id));
    }

    @GetMapping("/buscar-status-apartamento/{statusApartamento}")
    public ResponseEntity<?> buscarStatus(@PathVariable StatusApartamento statusApartamento)
    {
        return ResponseEntity.ok(this.apartamentoService.buscarPorStatus(statusApartamento));
    }

    @GetMapping("/buscar-bloco/{blocoApt}")
    public ResponseEntity<?> buscarBloco(@PathVariable String blocoApt)
    {
        return ResponseEntity.ok(this.apartamentoService.buscarPorBloco(blocoApt));
    }

    @DeleteMapping("/desativar-apartamento/{id}")
    public ResponseEntity<?> desativar(@PathVariable Long id)
    {
        return ResponseEntity.ok(this.apartamentoService.desativarApto(id));
    }
}
