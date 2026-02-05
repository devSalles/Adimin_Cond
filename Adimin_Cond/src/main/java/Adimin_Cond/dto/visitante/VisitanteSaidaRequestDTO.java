package Adimin_Cond.dto.visitante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VisitanteSaidaRequestDTO(
        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Nome obrigatório")
        String documento,

        @NotNull(message = "ID de morador obrigatório")
        Long idMorador
) {

}
