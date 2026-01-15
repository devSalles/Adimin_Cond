package Adimin_Cond.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "visitantes")
@Getter
@Setter
public class Visitante {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String documento;

    @Column(nullable = false)
    private LocalDateTime dataEntrada;

    @Column(nullable = false)
    private LocalDateTime dataSaida;

    @ManyToOne
    @JoinColumn(name = "morador_id")
    private Morador morador;
}
