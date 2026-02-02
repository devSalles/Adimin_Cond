package Adimin_Cond.entity;

import Adimin_Cond.Enum.StatusVeiculo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "veiculos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String placa;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String cor;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

    @OneToMany(mappedBy = "veiculo",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Acesso> acessos = new ArrayList<>();

}
