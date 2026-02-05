package Adimin_Cond.dto.visitante;

import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.Visitante;
import jakarta.validation.constraints.*;

public record VistanteEntradaRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "Nome obrigatório")
        String documento,

        @NotNull(message = "ID de morador obrigatório")
        Long idMorador
) {
    public Visitante toVisitante(Morador morador)
    {
        Visitante visitante = new Visitante();

        visitante.setNome(this.nome);
        visitante.setDocumento(this.documento);
        visitante.setMorador(morador);

        return visitante;
    }
}
