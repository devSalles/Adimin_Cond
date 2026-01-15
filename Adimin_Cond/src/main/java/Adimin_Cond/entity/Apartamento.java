package Adimin_Cond.entity;

import Adimin_Cond.Enum.StatusApartamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "apartamentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apartamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer numero;

    @Column(nullable = false)
    private String bloco;

    @Column(nullable = false)
    private Integer andar;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusApartamento status;

    @OneToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;

}
