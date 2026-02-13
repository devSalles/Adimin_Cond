package Adimin_Cond.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaxaCondScheduled {

    private final TaxaCondominioService taxaCondominioService;

    @Scheduled(cron = "${taxa.scheduler.cron:0 0 0 * * *}",
    zone = "America/Sao_Paulo")
    public void  verificarVencimento()
    {

        log.info("Iniciando veirificação ");

        try
        {
            log.info("Atualizando taxas");

            this.taxaCondominioService.atualizarTaxasAtrasadas();

            log.info("Taxas atualizadas, processo finalizado");

        } catch (Exception ex)
        {
            log.error("Erro ao atualizar taxas atrasadas");
        }
    }
}
