package Adimin_Cond.dto.apartamento;

import jakarta.validation.constraints.NotNull;

public record VincularAptoRequestDTO(

        @NotNull(message = "ID de apartamento obrigatorio")
        Long IdApartamento,

        @NotNull(message = "ID de morador obrigatório")
        Long IdMorador
) {
}
