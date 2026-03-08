package Adimin_Cond.repository;

import Adimin_Cond.Enum.TipoAcesso;
import Adimin_Cond.entity.Acesso;
import Adimin_Cond.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AcessoRepository extends JpaRepository<Acesso,Long> {

    //Pesquisar datas de entrada entre periodos
    List<Acesso> findByDataHoraEntradaBetween(LocalDateTime inicio, LocalDateTime fim);

    //Pesquisar datas de saída entre periodos
    List<Acesso> findByDataHoraSaidaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Acesso> findByTipoAcesso(TipoAcesso tipoAcesso);

    //Pesquisar veiculo e checar data e hora de entrada
    Optional<Acesso> findTopByVeiculoOrderByDataHoraEntradaDesc(Veiculo veiculo);

    //Pesquisar veiculo e checar data e hora de saida e se a entrada não e nula
    Optional<Acesso> findTopByVeiculoAndDataHoraSaidaIsNullOrderByDataHoraEntradaDesc(Veiculo veiculo);

    //Pesquisar veiculo por ID
    List<Acesso> findByVeiculoId(Long id);
}
