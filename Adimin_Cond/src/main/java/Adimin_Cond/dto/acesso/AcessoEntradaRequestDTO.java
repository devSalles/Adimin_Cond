package Adimin_Cond.dto.acesso;

import Adimin_Cond.entity.Acesso;
import Adimin_Cond.entity.Veiculo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AcessoEntradaRequestDTO(

        @NotBlank(message = "Porteiro obrigatório")
        String porteiro,

        @NotNull(message = "ID de veículo obrigatório")
        Long veiculo
) {

    public Acesso toAcesso(Veiculo veiculo)
    {
        Acesso acesso = new Acesso();

        acesso.setPorteiro(this.porteiro);
        acesso.setVeiculo(veiculo);

        return acesso;
    }
}
