package Adimin_Cond.dto.visitante;

import jakarta.validation.constraints.NotNull;

public record VincularVisitanteRequestDTO(

        @NotNull(message = "ID de morador obrigatório")
        Long idMorador,

        @NotNull(message = "ID de visitante obrigatório")
        Long idVisitante
) {
}
