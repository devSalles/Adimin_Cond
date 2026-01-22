package Adimin_Cond.repository;

import Adimin_Cond.entity.Apartamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApartamentoRepository extends JpaRepository<Apartamento,Long> {
}
