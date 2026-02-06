package Adimin_Cond.dto.visitante;

import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.Visitante;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record VistanteEntradaRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Nome obrigatório") @CPF(message = "Formtado de CPF inválido")
        String cpf,

        @NotNull(message = "ID de morador obrigatório")
        Long idMorador
) {
    public Visitante toVisitante(Morador morador)
    {
        Visitante visitante = new Visitante();

        visitante.setNome(this.nome);
        visitante.setCpf(this.cpf);
        visitante.setMorador(morador);

        return visitante;
    }
}
