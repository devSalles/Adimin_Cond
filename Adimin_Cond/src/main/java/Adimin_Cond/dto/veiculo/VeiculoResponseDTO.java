package Adimin_Cond.dto.veiculo;

import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.dto.visitante.VisitanteResponseDTO;
import Adimin_Cond.entity.Veiculo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record VeiculoResponseDTO(

        Long id,
        String placa,
        String modelo,
        String cor,
        StatusVeiculo status
) {

    public static VeiculoResponseDTO fromVeiculo(Veiculo veiculo)
    {
        return new VeiculoResponseDTO(veiculo.getId(), veiculo.getPlaca(), veiculo.getModelo(), veiculo.getCor(),veiculo.getStatus());
    }
}
