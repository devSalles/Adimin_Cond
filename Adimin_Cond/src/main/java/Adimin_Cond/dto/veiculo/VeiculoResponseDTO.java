package Adimin_Cond.dto.veiculo;

import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.entity.Veiculo;

public record VeiculoResponseDTO(
        Long id,
        String placa,
        String modelo,
        String cor,
        StatusVeiculo status,
        Long idMorador
) {

    public static VeiculoResponseDTO fromVeiculo(Veiculo veiculo)
    {
        return new VeiculoResponseDTO(veiculo.getId(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getCor(),veiculo.getStatus()
        ,veiculo.getMorador().getId());
    }
}
