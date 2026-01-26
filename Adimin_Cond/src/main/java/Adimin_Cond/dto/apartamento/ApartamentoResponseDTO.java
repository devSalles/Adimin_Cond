package Adimin_Cond.dto.apartamento;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.entity.Apartamento;

public record ApartamentoResponseDTO(
        Long id,
        String bloco,
        Integer numero,
        Integer andar,
        StatusApartamento status

) {

    public static ApartamentoResponseDTO fromApartamento(Apartamento apartamento)
    {
        return new ApartamentoResponseDTO(apartamento.getId(),apartamento.getBloco(),apartamento.getNumero(),apartamento.getAndar(),apartamento.getStatusApartamento());
    }
}
