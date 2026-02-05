package Adimin_Cond.dto.visitante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VisitanteSaidaRequestDTO(

        @NotNull(message = "ID de morador obrigatório")
        Long idMorador
) {

}
