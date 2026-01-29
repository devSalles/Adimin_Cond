package Adimin_Cond.repository;

import Adimin_Cond.entity.Morador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MoradorRepository extends JpaRepository<Morador,Long> {

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);

    boolean existsByTelefone(String telefone);

    Morador findByCpf(String cpf);

    Morador findByEmail(String cpf);
}
