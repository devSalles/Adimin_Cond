package Adimin_Cond.dto.veiculo;

import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.Veiculo;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


public record VeiculoRequestDTO(

        @NotNull(message = "Cor obrigatória") @NotBlank(message = "Placa do veículo obrigatória")
        @Pattern(regexp = "^([A-Z]{3}-[0-9]{4}|[A-Z]{3}[0-9][A-Z][0-9]{2})$",
                message = "Placa inválida. Use formato antigo (ABC-1234) ou Mercosul (ABC1D23)")
        String placa,

        @NotNull(message = "Cor obrigatória") @NotBlank(message = "Modelo obrigatório")
        String modelo,

        @NotNull(message = "Cor obrigatória") @NotBlank(message = "Cor obrigatória")
        String cor,

        Long idMorador
) {
    public Veiculo toVeiculo(Morador morador)
    {
        Veiculo veiculo = new Veiculo();

        veiculo.setPlaca(this.placa);
        veiculo.setModelo(this.modelo);
        veiculo.setCor(this.cor);
        veiculo.setMorador(morador);

        return veiculo;
    }
}
