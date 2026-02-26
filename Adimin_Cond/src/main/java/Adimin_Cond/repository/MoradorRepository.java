package Adimin_Cond.repository;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.entity.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MoradorRepository extends JpaRepository<Morador,Long> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByTelefone(String telefone);

    List<Morador> findByNome(String nome);

    Optional<Morador> findByCpf(String cpf);

    Optional<Morador> findByEmail(String cpf);

    List<Morador>findByStatus(StatusMorador statusMorador);
}
