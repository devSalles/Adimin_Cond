package Adimin_Cond.dto.morador;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.util.ArrayList;

public record MoradorRequestDTO(

        @NotBlank(message = "Nome obrigatório")
        String nome,

        @NotBlank(message = "CPF obrigatório") @CPF(message = "CPF com formato inválido")
        String cpf,

        @NotBlank(message = "Email obrigatório") @Email(message = "Email com formato inválido")
        String email,

        @NotBlank(message = "Telefone obrigatório")
        String telefone,

        @NotNull(message = "Status obrigatório")
        @Enumerated(EnumType.STRING)
        StatusMorador status,

        @NotNull(message = "Id de apartamento obrigatório")
        Long idApartamento,

        Long idVeiculos,

        @NotNull(message = "Id de visitante obrigatório")
        Long idVisitantes,

        @NotNull(message = "Id de taxa de condomínio obrigatório")
        Long idTaxaCondominio
) {
    public Morador toMorador(Apartamento apartamento,Veiculo veiculos, Visitante visitantes, TaxaCondominio taxaCondominio)
    {
        Morador morador = new Morador();
        morador.setNome(this.nome);
        morador.setCpf(this.cpf);
        morador.setEmail(this.email);
        morador.setTelefone(this.telefone);
        morador.setStatus(this.status);

        if(morador.getApartamento()==null)
        {
            morador.setApartamento(apartamento);
        }

        if(veiculos!=null)
        {
            if (morador.getVeiculos() == null)
            {
                morador.setVeiculos(new ArrayList<>());
            }

            morador.getVeiculos().add(veiculos);
        }

        if(visitantes != null)
        {
            if(morador.getVisitantes()==null)
            {
                morador.setVisitantes(new ArrayList<>());
            }

            morador.getVisitantes().add(visitantes);
        }

        if(taxaCondominio!=null)
        {
            if(morador.getTaxaCondominio()==null)
            {
                morador.setTaxaCondominio(new ArrayList<>());
            }

            morador.getTaxaCondominio().add(taxaCondominio);
        }

        return morador;
    }
}
