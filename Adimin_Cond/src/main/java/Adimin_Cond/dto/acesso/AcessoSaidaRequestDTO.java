package Adimin_Cond.dto.acesso;


import jakarta.validation.constraints.NotNull;

public record AcessoSaidaRequestDTO(

        @NotNull(message = "ID de veículo obrigatório")
        Long veiculoId,

        String porteiro
) {
}
