package Adimin_Cond.dto.morador;

import Adimin_Cond.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record MoradorRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
        String nome,

        @NotBlank(message = "CPF obrigatório") @CPF(message = "CPF com formato inválido")
        String cpf,

        @NotBlank(message = "Email obrigatório") @Email(message = "Email com formato inválido")
        String email,

        @NotBlank(message = "Telefone obrigatório")
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?9?\\d{4}-?\\d{4}$", message = "Telefone inválido")
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
