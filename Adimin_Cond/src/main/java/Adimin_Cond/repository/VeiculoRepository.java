package Adimin_Cond.repository;

import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.dto.veiculo.VeiculoRequestDTO;
import Adimin_Cond.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo,Long> {

    boolean existsByPlaca(String placa);

    Veiculo findByPlaca(String placa);

    List<Veiculo> findByStatus(StatusVeiculo statusVeiculo);

}
