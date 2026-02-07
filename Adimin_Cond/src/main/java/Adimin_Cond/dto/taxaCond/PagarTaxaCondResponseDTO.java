package Adimin_Cond.dto.taxaCond;

import Adimin_Cond.Enum.StatusTaxa;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.time.LocalDate;

public record PagarTaxaCondResponseDTO(

        Double valorFinal,

        LocalDate dataPagamento,

        LocalDate dataVencimento,

        StatusTaxa status
) {
}
