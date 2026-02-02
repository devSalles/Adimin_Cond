package Adimin_Cond.repository;

import Adimin_Cond.entity.Acesso;
import Adimin_Cond.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcessoRepository extends JpaRepository<Acesso,Long> {

    Acesso findByVeiculoAndDataHoraSaidaIsNull(Veiculo veiculo);
}
