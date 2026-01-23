package Adimin_Cond.dto.taxaCond;

import Adimin_Cond.Enum.StatusTaxa;
import Adimin_Cond.entity.TaxaCondominio;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TaxaCondResponseDTO(
        Long id,
        String referencia,
        BigDecimal valor,
        LocalDate dataVencimento,
        StatusTaxa status
) {

    public static TaxaCondResponseDTO fromTaxaCond(TaxaCondominio taxa)
    {
        return new TaxaCondResponseDTO(taxa.getId(), taxa.getReferencia(),taxa.getValor(),taxa.getDataVencimento(),taxa.getStatus());
    }
}
