package Adimin_Cond.repository;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.entity.Apartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento,Long> {

    List<Apartamento> findByStatusApartamento(StatusApartamento statusApartamento);

    List<Apartamento> findByBloco(String bloco);
}
