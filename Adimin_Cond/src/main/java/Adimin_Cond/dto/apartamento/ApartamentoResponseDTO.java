package Adimin_Cond.dto.apartamento;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.entity.Apartamento;

public record ApartamentoResponseDTO(
        Long id,
        String bloco,
        Integer numero,
        Integer andar,
        String observacoes,
        StatusApartamento status,
        Long idMorador
) {

    public static ApartamentoResponseDTO fromApartamento(Apartamento apto)
    {
        return new ApartamentoResponseDTO(apto.getId(),apto.getBloco(),apto.getNumero(),apto.getAndar(), apto.getObservacoes(),apto.getStatusApartamento()
        ,apto.getMorador().getId());
    }
}
