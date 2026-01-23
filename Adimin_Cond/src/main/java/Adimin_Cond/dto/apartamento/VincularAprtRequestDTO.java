package Adimin_Cond.dto.apartamento;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.entity.Apartamento;
import Adimin_Cond.entity.Morador;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ApartamentoRequestDTO(

        @NotNull(message = "Número obrigatório") @Positive(message = "Número inválido")
        Integer numero,

        @NotBlank(message = "Bloco obrigatório")
        String bloco,

        @NotNull(message = "Andar obrigatório") @Positive(message = "Andar inválido")
        Integer andar,

        @NotNull(message = "Status obrigatório") @Enumerated(EnumType.STRING)
        StatusApartamento status,

        @NotNull(message = "ID de morador obrigatório")
        Long Idmorador
) {
    public Apartamento toApartamento(Morador morador)
    {
        Apartamento apartamento = new Apartamento();

        apartamento.setNumero(this.numero);
        apartamento.setBloco(this.bloco);
        apartamento.setAndar(this.andar);
        apartamento.setStatus(this.status);

        if(apartamento.getMorador() == null)
        {
            apartamento.setMorador(morador);
        }
        return apartamento;
    }

}
