package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusTaxa;
import Adimin_Cond.core.exception.DataException;
import Adimin_Cond.core.exception.IdNaoEncontradoException;
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
import java.time.LocalDateTime;
import java.time.LocalTime;
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

        boolean referenciaRepetida = this.taxaCondominioRepository.existsByReferencia(taxaDTO.referencia());
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
     public TaxaCondResponseDTO pagarTaxa(Long id)
    {
        TaxaCondominio taxa = this.taxaCondominioRepository.findById(id).orElseThrow(()-> new IdNaoEncontradoException("Taxa no encontrada"));

        if(taxa.getStatus() == StatusTaxa.PAGA)
        {
            throw new TaxaJaPagaException("A taxa já foi paga");
        }

        if(taxa.getDataPagamento().isBefore(taxa.getDataVencimento()))
        {
            double valorFinalTaxa = taxa.getMulta() + taxa.getValor();

            taxa.setValor(valorFinalTaxa);
            taxa.setStatus(StatusTaxa.PAGA);
            taxa.setDataPagamento(LocalDate.now());

            this.taxaCondominioRepository.save(taxa);

            return TaxaCondResponseDTO.fromTaxaCond(taxa);
        }

        taxa.setStatus(StatusTaxa.PAGA);
        taxa.setDataPagamento(LocalDate.now());
        this.taxaCondominioRepository.save(taxa);

        return TaxaCondResponseDTO.fromTaxaCond(taxa);
    }

    public TaxaCondResponseDTO buscarID(Long id)
    {
        TaxaCondominio taxa = this.taxaCondominioRepository.findById(id).orElseThrow(()-> new IdNaoEncontradoException("Taxa no encontrada"));
        return TaxaCondResponseDTO.fromTaxaCond(taxa);
    }

    public List<TaxaCondResponseDTO> listarTodos()
    {
        List<TaxaCondominio> taxas = this.taxaCondominioRepository.findAll();

        if(taxas.isEmpty())
        {
            throw new IdNaoEncontradoException("Nenhum cadastro de taxas");
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
            throw new IdNaoEncontradoException("Nenhum cadastro de taxas nessa data");
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
            throw new IdNaoEncontradoException("Nenhum cadastro de taxas nessa data");
        }

        return taxas.stream().map(TaxaCondResponseDTO::fromTaxaCond).toList();
    }
}
