package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.core.exception.DataException;
import Adimin_Cond.core.exception.IdNaoEncontradoException;
import Adimin_Cond.core.exception.NenhumCadastroException;
import Adimin_Cond.core.exception.acesso.AcessoRestritoException;
import Adimin_Cond.core.exception.morador.MoradorInativoException;
import Adimin_Cond.core.exception.visitante.NomeDeVisitanteNaoEncontradoException;
import Adimin_Cond.core.exception.visitante.VisitaJaEmAndamentoException;
import Adimin_Cond.core.exception.visitante.VisitaJaFinalizadaException;
import Adimin_Cond.core.exception.visitante.VisitanteNaoEncontradoException;
import Adimin_Cond.dto.visitante.VisitanteResponseDTO;
import Adimin_Cond.dto.visitante.VisitanteSaidaRequestDTO;
import Adimin_Cond.dto.visitante.VistanteEntradaRequestDTO;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.Visitante;
import Adimin_Cond.repository.MoradorRepository;
import Adimin_Cond.repository.VisitanteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitanteService {

    private final VisitanteRepository visitanteRepository;
    private final MoradorRepository moradorRepository;

    @Transactional
    public VisitanteResponseDTO registrarVisita(VistanteEntradaRequestDTO visitaDTO)
    {
        Morador morador = this.moradorRepository.findById(visitaDTO.idMorador()).orElseThrow(()->new IdNaoEncontradoException("ID de morador não encontrado"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException("Morador está inativo e não pode receber visita");
        }

        boolean visitaAtiva = this.visitanteRepository.existsByCpfAndStatusVisitante(visitaDTO.cpf(),StatusVisitante.EM_VISITA);
        if(visitaAtiva)
        {
            throw new VisitaJaEmAndamentoException();
        }

        Visitante visitante = visitaDTO.toVisitante(morador);
        visitante.setStatusVisitante(StatusVisitante.EM_VISITA);
        visitante.setDataEntrada(LocalDateTime.now());

        this.visitanteRepository.save(visitante);

        return VisitanteResponseDTO.fromVisitante(visitante);
    }

    @Transactional
    public VisitanteResponseDTO registrarSaida(VisitanteSaidaRequestDTO visitaDTO)
    {
        Visitante visitante = this.visitanteRepository.findByCpfAndStatusVisitante(visitaDTO.cpf(),StatusVisitante.EM_VISITA)
                .orElseThrow(() -> new AcessoRestritoException("Visitante sem visita ativa"));

        //Proteção de regra de negócio
        LocalDateTime agora = LocalDateTime.now();
        if(agora.isBefore(visitante.getDataEntrada()))
        {
            throw new DataException("Data de entrada de visitante não pode ser maior que data futura");
        }

        visitante.setDataSaida(agora);
        visitante.setStatusVisitante(StatusVisitante.FINALIZADA);

        this.visitanteRepository.save(visitante);

        return VisitanteResponseDTO.fromVisitante(visitante);
    }

    public List<VisitanteResponseDTO> listarTodas()
    {
        List<Visitante> visitantes = this.visitanteRepository.findAll();

        if(visitantes.isEmpty())
        {
            throw new NenhumCadastroException("Nnenhuma visita registrada");
        }

        return visitantes.stream().map(VisitanteResponseDTO::fromVisitante).toList();
    }

    public VisitanteResponseDTO buscarID(Long id)
    {
        Visitante visitante = this.visitanteRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("Id de visitante não encontrado"));
        return VisitanteResponseDTO.fromVisitante(visitante);
    }

    public VisitanteResponseDTO buscarPorCPF(String cpf)
    {
        Visitante visitanteCPF = this.visitanteRepository.findByCpf(cpf).orElseThrow(VisitanteNaoEncontradoException::new);
        return VisitanteResponseDTO.fromVisitante(visitanteCPF);
    }

    public VisitanteResponseDTO buscarPorNome(String nome)
    {
        Visitante visitanteNome = this.visitanteRepository.findByNome(nome).orElseThrow(NomeDeVisitanteNaoEncontradoException::new);
        return VisitanteResponseDTO.fromVisitante(visitanteNome);
    }

    public List<VisitanteResponseDTO> pesquisarPeriodoDataEntrada(LocalDate inicio, LocalDate fim)
    {
        if(fim.isBefore(inicio))
        {
            throw new DataException("Data futura não pode ser maior que data pasada");
        }

        LocalDateTime inicioFormatado = inicio.atStartOfDay();
        LocalDateTime finalFormatado = fim.atTime(LocalTime.MAX);

        List<Visitante> visitantes = this.visitanteRepository.findByDataEntradaBetween(inicioFormatado,finalFormatado);

        return visitantes.stream().map(VisitanteResponseDTO::fromVisitante).toList();
    }

    public List<VisitanteResponseDTO> pesquisarPeriodoDataSaida(LocalDate inicio, LocalDate fim)
    {
        if(fim.isBefore(inicio))
        {
            throw new DataException("Data futura não pode ser maior que data pasada");
        }

        LocalDateTime inicioFormatado = inicio.atStartOfDay();
        LocalDateTime finalFormatado = fim.atTime(LocalTime.MAX);

        List<Visitante> visitantes = this.visitanteRepository.findByDataSaidaBetween(inicioFormatado,finalFormatado);

        return visitantes.stream().map(VisitanteResponseDTO::fromVisitante).toList();
    }
}