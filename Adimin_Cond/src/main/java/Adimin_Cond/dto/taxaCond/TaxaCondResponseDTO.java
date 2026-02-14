package Adimin_Cond.dto.taxaCond;

import Adimin_Cond.Enum.StatusTaxa;
import Adimin_Cond.entity.TaxaCondominio;
import java.time.LocalDate;

public record TaxaCondResponseDTO(
        Long id,
        String referencia,
        Double valorFinal,
        LocalDate dataVencimento,
        LocalDate dataPagamento,
        StatusTaxa status,
        Long idMorador
) {

    public static TaxaCondResponseDTO fromTaxaCond(TaxaCondominio taxa)
    {
        return new TaxaCondResponseDTO(taxa.getId(), taxa.getReferencia(),taxa.getValor(),taxa.getDataVencimento(),
                taxa.getDataPagamento(),taxa.getStatus(),taxa.getMorador().getId());
    }
}
