package Adimin_Cond.dto.morador;

import jakarta.validation.constraints.NotNull;

public record VincularMoradorRequestDTO(

        @NotNull(message = "Id de morador obrigatório")
        Long idMorador,

        @NotNull(message = "Id de apartamento obrigatório")
        Long idApartamento,

        Long idVeiculos,

        @NotNull(message = "Id de visitante obrigatório")
        Long idVisitantes,

        @NotNull(message = "Id de taxa de condomínio obrigatório")
        Long idTaxaCondominio
) {
}
