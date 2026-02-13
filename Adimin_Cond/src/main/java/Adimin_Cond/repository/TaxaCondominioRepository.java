package Adimin_Cond.repository;

import Adimin_Cond.entity.TaxaCondominio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaxaCondominioRepository extends JpaRepository<TaxaCondominio,Long> {

    boolean existsByReferencia(String referencia);

    List<TaxaCondominio>findByDataPagamentoBetween(LocalDate inicio, LocalDate fim);


    List<TaxaCondominio> findByDataVencimentoBetween(LocalDate inicio, LocalDate fim);


    //Metodo responsável por tonar as taxas atrasadas
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE TaxaCondominio t SET t.status = Adimin_Cond.Enum.StatusTaxa.ATRASADA WHERE t.status = Adimin_Cond.Enum.StatusTaxa.PENDENTE AND t.dataVencimento < CURRENT_DATE")
    int atualizarTaxasAtrasadas();
}
