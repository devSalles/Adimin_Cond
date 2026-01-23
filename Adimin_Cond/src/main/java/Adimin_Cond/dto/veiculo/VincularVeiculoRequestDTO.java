package Adimin_Cond.dto.veiculo;

import jakarta.validation.constraints.NotNull;

public record VincularVeiculoRequestDTO(

        @NotNull(message = "ID do morador obrigatório")
        Long IdMorador,
        @NotNull(message = "ID de veículo obrigatório")
        Long IdVeiculo
) {
}
