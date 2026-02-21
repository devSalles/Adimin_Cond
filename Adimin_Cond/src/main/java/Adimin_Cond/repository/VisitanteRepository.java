package Adimin_Cond.repository;

import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.entity.Visitante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitanteRepository extends JpaRepository<Visitante,Long> {

    boolean existsByCpfAndStatusVisitante(String visita, StatusVisitante statusVisitante);

    Optional<Visitante> findByCpfAndStatusVisitante(String visita, StatusVisitante statusVisitante);

    Optional<Visitante> findByCpf(String cpf);

    Optional<Visitante>findByNome(String nome);

    List<Visitante> findByDataEntradaBetween(LocalDateTime inicio, LocalDateTime fim);

    List<Visitante> findByDataSaidaBetween(LocalDateTime inicio, LocalDateTime fim);

    boolean existsByMoradorIdAndStatusVisitante(Long idMorador, StatusVisitante statusVisitantes);
}
