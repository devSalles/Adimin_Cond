package Adimin_Cond.dto.morador;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.entity.Morador;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.br.CPF;

public record MoradorUpdateRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "Email obrigatório") @Email(message = "Email com formato inválido")
        String email,

        @NotNull(message = "Status de morador obrigatório")
        StatusMorador statusMorador
){
    public Morador updateMorador(Morador morador)
    {
        morador.setNome(this.nome);
        morador.setEmail(this.email);
        morador.setStatus(statusMorador);

        return morador;
    }
}
