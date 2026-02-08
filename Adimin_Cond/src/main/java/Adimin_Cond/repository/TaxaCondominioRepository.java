package Adimin_Cond.repository;

import Adimin_Cond.entity.TaxaCondominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaxaCondominioRepository extends JpaRepository<TaxaCondominio,Long> {

    boolean existsByReferencia(String referencia);

    List<TaxaCondominio>findByDataPagamentoBetween(LocalDate inicio, LocalDate fim);


    List<TaxaCondominio> findByDataVencimentoBetween(LocalDate inicio, LocalDate fim);
}
