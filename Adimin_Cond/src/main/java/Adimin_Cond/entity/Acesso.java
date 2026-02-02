package Adimin_Cond.entity;

import Adimin_Cond.Enum.TipoAcesso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Acesso {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime dataHoraEntrada;

    @Column
    private LocalDateTime dataHoraSaida;

    @Column(nullable = false)
    private String porteiro;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoAcesso tipo;

    @ManyToOne
    @JoinColumn(name = "veiculo_id",nullable = false)
    private Veiculo veiculo;
}
