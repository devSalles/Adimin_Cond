package Adimin_Cond.entity;

import Adimin_Cond.Enum.StatusMorador;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "moradores")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Morador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String telefone;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMorador status;

    @OneToOne(mappedBy = "morador")
    private Apartamento apartamento;

    @OneToMany(mappedBy = "morador")
    private List<Veiculo> veiculos = new ArrayList<>();

    @OneToMany(mappedBy = "morador")
    private List<Visitante> visitantes = new ArrayList<>();

    @OneToMany(mappedBy = "morador")
    private List<TaxaCondominio> taxaCondominio = new ArrayList<>();
}
