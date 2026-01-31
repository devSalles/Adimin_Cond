package Adimin_Cond.dto.apartamento;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.dto.morador.MoradorResponseDTO;
import Adimin_Cond.entity.Apartamento;

public record VincularAptoResponseDTO(
        Long id,
        String bloco,
        Integer numero,
        Integer andar,
        StatusApartamento status,
        String observacoes,
        MoradorResponseDTO morador
) {
    public static VincularAptoResponseDTO fromApartamento(Apartamento apto)
    {
        return new VincularAptoResponseDTO(apto.getId(),apto.getBloco(),apto.getNumero(),apto.getAndar(),apto.getStatusApartamento(),
                apto.getObservacoes(), apto.getMorador() != null ? MoradorResponseDTO.fromMorador(apto.getMorador()) : null);
    }
}
