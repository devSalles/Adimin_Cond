package Adimin_Cond.dto.apartamento;

import Adimin_Cond.entity.Apartamento;
import Adimin_Cond.entity.Morador;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VincularAprtRequestDTO(

        @NotNull(message = "ID de apartamento obrigatorio")
        Long IdApartamento,

        @NotNull(message = "ID de morador obrigatório")
        Long IdMorador
) {
}
