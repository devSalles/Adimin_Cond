package Adimin_Cond.dto.visitante;

import Adimin_Cond.entity.Visitante;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.time.LocalDateTime;

public record VisitanteResponseDTO(
        Long id,
        String nome,
        String documento,
        LocalDateTime dataEntrada,
        LocalDateTime dataSaida
) {

    public static VisitanteResponseDTO fromVisitante(Visitante visitante)
    {
        return new VisitanteResponseDTO(visitante.getId(), visitante.getNome(), visitante.getDocumento(), visitante.getDataEntrada(),visitante.getDataSaida());
    }
}
