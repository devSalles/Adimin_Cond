package Adimin_Cond.dto.morador;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.TaxaCondominio;
import Adimin_Cond.entity.Veiculo;
import Adimin_Cond.entity.Visitante;

import java.util.List;

public record MoradorResponseDTO(
        Long id,
        String nome,
        String cpf,
        String email,
        String telefone,
        StatusMorador status,

        Long apartamentoId,
        List<Long> taxaCondominioId,
        List<Long> veiculoId,
        List<Long> visitanteId
) {
    public static MoradorResponseDTO fromMorador(Morador morador)
    {
        return new MoradorResponseDTO(morador.getId(), morador.getNome(),morador.getCpf(), morador.getEmail(),morador.getTelefone(),morador.getStatus(),

                morador.getApartamento() !=null ? morador.getApartamento().getId() : null,
                morador.getTaxaCondominio() != null ? morador.getTaxaCondominio().stream().map(TaxaCondominio::getId).toList() : List.of(),
                morador.getVeiculos() != null ? morador.getVeiculos().stream().map(Veiculo::getId).toList() : List.of(),
                morador.getVisitantes()!= null ? morador.getVisitantes().stream().map(Visitante::getId).toList() : List.of()
        );
    }
}
