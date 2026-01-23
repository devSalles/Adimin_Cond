package Adimin_Cond.dto.taxaCond;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VincularTaxaCondRequestDTO(

        @NotNull(message = "ID de morador obrigatório") @Positive(message = "Valor inválido")
        Long  idMorador,

        @NotNull(message = "ID de morador obrigatório") @Positive(message = "Valor inválido")
        Long  idTaxaCondominio
) {
}
