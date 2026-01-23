package Adimin_Cond.dto.morador;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.dto.apartamento.ApartamentoResponseDTO;
import Adimin_Cond.dto.taxaCond.TaxaCondResponseDTO;
import Adimin_Cond.dto.veiculo.VeiculoResponseDTO;
import Adimin_Cond.dto.visitante.VisitanteResponseDTO;
import Adimin_Cond.entity.Morador;

import java.util.List;

public record MoradorResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        StatusMorador status,

        List<VeiculoResponseDTO> veiculoResponseDTO,
        List<VisitanteResponseDTO> visitanteResponseDTO,
        List<TaxaCondResponseDTO> taxaCondResponseDTO,
        ApartamentoResponseDTO apartamentoResponseDTO
) {
    public static MoradorResponseDTO fromMorador(Morador morador)
    {
        return new MoradorResponseDTO(morador.getId(), morador.getNome(),morador.getCpf(), morador.getEmail(),morador.getTelefone(),morador.getStatus(),

                morador.getVeiculos() != null ? morador.getVeiculos().stream().map(VeiculoResponseDTO::fromVeiculo).toList() : null,
                morador.getVisitantes() != null ? morador.getVisitantes().stream().map(VisitanteResponseDTO::fromVisitante).toList() : null,
                morador.getTaxaCondominio() != null ? morador.getTaxaCondominio().stream().map(TaxaCondResponseDTO::fromTaxaCond).toList() : null,
                morador.getApartamento() != null ? ApartamentoResponseDTO.fromApartamento(morador.getApartamento()) : null);
    }
}
