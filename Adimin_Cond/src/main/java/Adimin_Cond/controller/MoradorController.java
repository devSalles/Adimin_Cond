package Adimin_Cond.controller;

import Adimin_Cond.Enum.StatusMorador;
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
@RequestMapping("/morador")
@RequiredArgsConstructor
@Tag(name = "Morador")
public class MoradorController {

    private final MoradorService moradorService;

    @PostMapping("/adicionar")
    public ResponseEntity<?> salvar(@Valid @RequestBody MoradorRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(moradorService.salvar(dto));
    }

    @PutMapping("/atualizar-morador/{id}")
    public  ResponseEntity<?> atualizarMorador(@PathVariable Long id, @Valid @RequestBody MoradorUpdateRequestDTO dto)
    {
        return ResponseEntity.ok(this.moradorService.atualizarMorador(id,dto));
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<?> listarTodosMoradores()
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

    @GetMapping("/buscar-por-status/{status}")
    public ResponseEntity<?> buscarPorStatus(@PathVariable StatusMorador status)
    {
        return ResponseEntity.ok(this.moradorService.buscarPorStatus(status));
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
