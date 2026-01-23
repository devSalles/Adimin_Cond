package Adimin_Cond.dto.apartamento;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.entity.Apartamento;

public record ApartamentoResponseDTO(

        String bloco,
        Integer numero,
        Integer andar,
        StatusApartamento status,
        Long Idmorador

) {

    public static ApartamentoResponseDTO fromApartamento(Apartamento apartamento)
    {
        return new ApartamentoResponseDTO(apartamento.getBloco(),apartamento.getNumero(),apartamento.getAndar(),apartamento.getStatus(),apartamento.getMorador().getId());
    }
}
