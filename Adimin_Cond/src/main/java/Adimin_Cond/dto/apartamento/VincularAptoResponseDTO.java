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
        MoradorResponseDTO moradorResponseDTO
) {
    public static VincularAptoResponseDTO fromApartamento(Apartamento apartamento)
    {
        return new VincularAptoResponseDTO(apartamento.getId(),apartamento.getBloco(),apartamento.getNumero(),apartamento.getAndar(),apartamento.getStatusApartamento(),
                apartamento.getMorador() != null ? MoradorResponseDTO.fromMorador(apartamento.getMorador()) : null);
    }
}
