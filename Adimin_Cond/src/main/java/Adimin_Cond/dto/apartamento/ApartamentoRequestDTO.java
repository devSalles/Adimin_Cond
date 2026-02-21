package Adimin_Cond.dto.apartamento;

import Adimin_Cond.entity.Apartamento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ApartamentoRequestDTO(
        @NotNull(message = "Número obrigatório") @Positive(message = "Número inválido")
        Integer numero,

        @NotBlank(message = "Bloco de apartamento obrigatório")
        String bloco,

        @NotNull(message = "Andar obrigatório") @Positive(message = "Andar inválido")
        Integer andar,

        String observacoes
) {
    public Apartamento toApartamento()
    {
        Apartamento apartamento = new Apartamento();

        apartamento.setNumero(this.numero);
        apartamento.setBloco(this.bloco);
        apartamento.setAndar(this.andar);
        apartamento.setObservacoes(this.observacoes);

        return apartamento;
    }


    public Apartamento updateApartamento(Apartamento apartamento)
    {
        apartamento.setNumero(this.numero);
        apartamento.setBloco(this.bloco);
        apartamento.setAndar(this.andar);
        apartamento.setObservacoes(this.observacoes);

        return apartamento;
    }
}
