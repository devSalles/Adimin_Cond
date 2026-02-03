package Adimin_Cond.repository;

import Adimin_Cond.Enum.TipoAcesso;
import Adimin_Cond.entity.Acesso;
import Adimin_Cond.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface AcessoRepository extends JpaRepository<Acesso,Long> {

    Acesso findByVeiculoAndDataHoraSaidaIsNull(Veiculo veiculo);

    List<Acesso> findByDataHoraEntradaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Acesso> findByDataHoraSaidaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Acesso> findByTipoAcesso(TipoAcesso tipoAcesso);
}
