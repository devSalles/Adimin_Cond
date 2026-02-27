package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusTaxa;
import Adimin_Cond.core.exception.DataException;
import Adimin_Cond.core.exception.IdNaoEncontradoException;
import Adimin_Cond.core.exception.NenhumCadastroException;
import Adimin_Cond.core.exception.PeriodoNaoEncontradoException;
import Adimin_Cond.core.exception.morador.MoradorInativoException;
import Adimin_Cond.core.exception.taxa.ReferenciaRepetidaException;
import Adimin_Cond.core.exception.taxa.TaxaJaPagaException;
import Adimin_Cond.dto.taxaCond.TaxaCondRequestDTO;
import Adimin_Cond.dto.taxaCond.TaxaCondResponseDTO;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.TaxaCondominio;
import Adimin_Cond.repository.MoradorRepository;
import Adimin_Cond.repository.TaxaCondominioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TaxaCondominioService {
    private final MoradorRepository moradorRepository;
    private final TaxaCondominioRepository taxaCondominioRepository;

    @Transactional
    public TaxaCondResponseDTO gerarTaxa(TaxaCondRequestDTO taxaDTO)
    {
        Morador morador = this.moradorRepository.findById(taxaDTO.idMorador()).orElseThrow(() -> new IdNaoEncontradoException("Id de morador não encontrado"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException("Morador inativo não pode receber taxa");
        }

        boolean referenciaRepetida = this.taxaCondominioRepository.existsByReferenciaAndMoradorId(taxaDTO.referencia() ,morador.getId());
        if(referenciaRepetida)
        {
            throw new ReferenciaRepetidaException("Não pode existir mais de uma taxa com a mesma referência para o mesmo morador");
        }

        TaxaCondominio taxa = taxaDTO.toTaxaCond(morador);
        taxa.setStatus(StatusTaxa.PENDENTE);
        this.taxaCondominioRepository.save(taxa);

        return TaxaCondResponseDTO.fromTaxaCond(taxa);
    }

    @Transactional
     public TaxaCondResponseDTO pagarTaxa(Long id,LocalDate dataPagamento)
    {
        TaxaCondominio taxa = this.taxaCondominioRepository.findById(id).orElseThrow(()-> new IdNaoEncontradoException("Taxa não encontrada"));

        if(taxa.getStatus() == StatusTaxa.PAGA)
        {
            throw new TaxaJaPagaException("A taxa já foi paga");
        }

        taxa.setDataPagamento(dataPagamento);
        taxa.setStatus(StatusTaxa.PAGA);

        if(taxa.getDataPagamento().isAfter(taxa.getDataVencimento()))
        {
            final Double multa = 0.08;
            double valorFinalTaxa = taxa.getValor() * (1 + multa);
            taxa.setValor(valorFinalTaxa);
        }
        else
        {
            taxa.setMulta(0.0);
        }

        this.taxaCondominioRepository.save(taxa);

        return TaxaCondResponseDTO.fromTaxaCond(taxa);
    }

    //Metodo responsável por atualizar status de taxas com pagamentos atrasados
    private void atualizarStatusAtrasado(TaxaCondominio taxa)
    {
        if(taxa.getStatus() == StatusTaxa.PENDENTE && taxa.getDataVencimento().isBefore(LocalDate.now()))
        {
            taxa.setStatus(StatusTaxa.ATRASADA);
        }
    }

    public TaxaCondResponseDTO buscarID(Long id)
    {
        TaxaCondominio taxa = this.taxaCondominioRepository.findById(id).orElseThrow(()-> new IdNaoEncontradoException("Taxa no encontrada"));

        atualizarStatusAtrasado(taxa);

        return TaxaCondResponseDTO.fromTaxaCond(taxa);
    }

    public List<TaxaCondResponseDTO> listarTodos()
    {
        List<TaxaCondominio> taxas = this.taxaCondominioRepository.findAll();

        if(taxas.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum cadastro de taxas");
        }

        for(TaxaCondominio taxasAtrasadas : taxas)
        {
            atualizarStatusAtrasado(taxasAtrasadas);
        }

        return taxas.stream().map(TaxaCondResponseDTO::fromTaxaCond).toList();
    }

    public List<TaxaCondResponseDTO> buscarPorDataDePagamento(LocalDate incio, LocalDate fim)
    {
        if(fim.isBefore(incio))
        {
            throw new DataException("Data futura não pode ser maior que data passada");
        }

        List<TaxaCondominio> taxas = this.taxaCondominioRepository.findByDataPagamentoBetween(incio,fim);

        if(taxas.isEmpty())
        {
            throw new PeriodoNaoEncontradoException();
        }

        return taxas.stream().map(TaxaCondResponseDTO::fromTaxaCond).toList();
    }

    public List<TaxaCondResponseDTO> buscarPorDataDeVencimento(LocalDate incio, LocalDate fim)
    {
        if(fim.isBefore(incio))
        {
            throw new DataException("Data futura não pode ser maior que data passada");
        }

        List<TaxaCondominio> taxas = this.taxaCondominioRepository.findByDataVencimentoBetween(incio,fim);

        if(taxas.isEmpty())
        {
            throw new PeriodoNaoEncontradoException();
        }

        return taxas.stream().map(TaxaCondResponseDTO::fromTaxaCond).toList();
    }
}
