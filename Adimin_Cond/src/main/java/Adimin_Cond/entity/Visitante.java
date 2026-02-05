package Adimin_Cond.entity;

import Adimin_Cond.Enum.StatusVisitante;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitantes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Visitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false,unique = true)
    private String documento;

    @Column(nullable = false)
    private LocalDateTime dataEntrada;

    @Column(nullable = false)
    private LocalDateTime dataSaida;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    public StatusVisitante statusVisitante;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;
}
