package Adimin_Cond.entity;

import Adimin_Cond.Enum.StatusTaxa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "taxasCondominio",
        uniqueConstraints = @UniqueConstraint(columnNames = {"morador_id", "referencia"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaxaCondominio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String referencia;

    @Column(nullable = false)
    private Double valor;

    @Column
    private Double multa=0.0;

    @Column
    private LocalDate dataPagamento;

    @Column(nullable = false)
    private LocalDate dataVencimento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusTaxa status;

    @ManyToOne
    @JoinColumn(name = "morador_id",nullable = false)
    private Morador morador;
}
