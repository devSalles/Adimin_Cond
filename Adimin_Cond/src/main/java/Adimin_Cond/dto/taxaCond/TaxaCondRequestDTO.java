package Adimin_Cond.dto.taxaCond;

import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.TaxaCondominio;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record TaxaCondRequestDTO(

        @NotBlank(message = "Referência de taxa obrigatória")
        String referencia,

        @NotNull(message = "Valor da taxa obrigatório") @PositiveOrZero(message = "Valor inválido")
        Double valor,

//        @NotNull(message = "Data de vencimento obrigatória") @Future(message = "A data de vencimento deve ser posterior a data atual")
        LocalDate dataVencimento,

//        @NotNull(message = "Data de pagamento obrigatória") @Future(message = "A data de pagamento deve ser posterior a data atual")
        LocalDate dataPagamento,

        Long idMorador
) {

    public TaxaCondominio toTaxaCond(Morador morador)
    {
        TaxaCondominio taxaCondominio = new TaxaCondominio();

        taxaCondominio.setReferencia(this.referencia);
        taxaCondominio.setValor(this.valor);
        taxaCondominio.setDataVencimento(this.dataVencimento);
        taxaCondominio.setDataPagamento(this.dataPagamento);
        taxaCondominio.setMorador(morador);

        return taxaCondominio;
    }
}
