package Adimin_Cond.dto.acesso;

import Adimin_Cond.Enum.TipoAcesso;
import Adimin_Cond.entity.Acesso;

import java.time.LocalDateTime;

public record AcessoResponseDTO(

        Long id,
        LocalDateTime dataHoraEntrada,
        LocalDateTime dataHoraSaida,
        String porteiro,
        TipoAcesso tipo,
        Long idVeiculo
) {

    public static AcessoResponseDTO fromAcesso(Acesso acesso) {
        return new AcessoResponseDTO(acesso.getId(), acesso.getDataHoraEntrada(),acesso.getDataHoraSaida(), acesso.getPorteiro(), acesso.getTipoAcesso(),
                acesso.getVeiculo().getId());
    }
}
