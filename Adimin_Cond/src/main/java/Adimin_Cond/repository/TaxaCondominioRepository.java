package Adimin_Cond.repository;

import Adimin_Cond.entity.TaxaCondominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxaCondominioRepository extends JpaRepository<TaxaCondominio,Long> {
}
