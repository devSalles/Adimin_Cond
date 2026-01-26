package Adimin_Cond.dto.visitante;

import Adimin_Cond.entity.Visitante;

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
