package Adimin_Cond.dto.visitante;

import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

public record VisitanteSaidaRequestDTO(

        @NotBlank(message = "Documento obrigatório") @CPF(message = "Formtado de CPF inválido")
        String cpf
) {}

