package Adimin_Cond.dto.taxaCond;

import Adimin_Cond.Enum.StatusTaxa;
import Adimin_Cond.entity.TaxaCondominio;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TaxaCondRequestDTO(

        @NotBlank(message = "Referência de taxa obrigatória")
        String referencia,

        @NotNull(message = "Valor da taxa obrigatório") @PositiveOrZero(message = "Valor inválido")
        BigDecimal valor,

        @NotNull(message = "Data de vencimento obrigatória") @Future(message = "A data de vencimento deve ser posterior a data atual")
        LocalDate dataVencimento,

        @NotNull(message = "Status da taxa obrigatório") @Enumerated(EnumType.STRING)
        StatusTaxa status
) {

    public TaxaCondominio toTaxaCond()
    {
        TaxaCondominio taxaCondominio = new TaxaCondominio();

        taxaCondominio.setValor(this.valor);
        taxaCondominio.setDataVencimento(this.dataVencimento);
        taxaCondominio.setStatus(this.status);

        return taxaCondominio;
    }
}
