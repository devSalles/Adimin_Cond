package Adimin_Cond.dto.visitante;

import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.Visitante;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

public record VistanteRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Nome obrigatório")
        String documento,

        @NotNull(message = "Data de entrada obrigatória") @PastOrPresent(message = "A data não pode ser futura a data atual")
        LocalDateTime dataEntrada,

        @NotNull(message = "Data de saída obrigatória") @FutureOrPresent(message = "A data de saída não pode ser no passado")
        LocalDateTime dataSaida,

        @NotNull(message = "status de visitante obrigatório")
        StatusVisitante statusVisitante,

        @NotNull(message = "ID de morador obrigatório")
        Long idMorador
) {
    public Visitante toVisitante(Morador morador)
    {
        Visitante visitante = new Visitante();

        visitante.setNome(this.nome);
        visitante.setDocumento(this.documento);
        visitante.setDataEntrada(this.dataEntrada);
        visitante.setDataSaida(this.dataSaida);
        visitante.setMorador(morador);

        return visitante;
    }
}
