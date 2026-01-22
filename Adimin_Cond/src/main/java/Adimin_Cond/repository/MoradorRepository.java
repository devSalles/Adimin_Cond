package Adimin_Cond.repository;

import Adimin_Cond.entity.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends JpaRepository<Morador,Long> {
}
