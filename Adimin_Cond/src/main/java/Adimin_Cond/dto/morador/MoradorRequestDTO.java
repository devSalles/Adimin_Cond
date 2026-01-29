package Adimin_Cond.dto.morador;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

public record MoradorRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "CPF obrigatório") @CPF(message = "CPF com formato inválido")
        String cpf,

        @NotBlank(message = "Email obrigatório") @Email(message = "Email com formato inválido")
        String email,

        @NotBlank(message = "Telefone obrigatório")
        String telefone
) {
    public Morador toMorador()
    {
        Morador morador = new Morador();
        morador.setNome(this.nome);
        morador.setCpf(this.cpf);
        morador.setEmail(this.email);
        morador.setTelefone(this.telefone);

        return morador;
    }
}
