package Adimin_Cond.repository;

import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante,Long> {

    boolean existsByDocumentoAndStatusVisitante(String visita, StatusVisitante statusVisitante);

    boolean existsByDocumento(String documento);

    Visitante findByMoradorAndDataSaidaIsNull(Morador morador);
}
