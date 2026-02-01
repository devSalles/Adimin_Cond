package Adimin_Cond.dto.veiculo;

import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.entity.Veiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VeiculoUpdateRequestDTO(

        @NotBlank(message = "Modelo obrigatório")
        String modelo,

        @NotBlank(message = "Cor obrigatória")
        String cor,

        @NotNull(message = "Status obrigatório")
        @Enumerated(EnumType.STRING)
        StatusVeiculo status
) {
    public Veiculo updateVeiculo(Veiculo veiculo)
    {
        veiculo.setModelo(this.modelo);
        veiculo.setCor(this.cor);
        veiculo.setStatus(this.status);

        return veiculo;
    }
}
