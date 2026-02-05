package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.core.exception.IdNaoEncontradoException;
import Adimin_Cond.core.exception.acesso.AcessoRestritoException;
import Adimin_Cond.core.exception.morador.MoradorInativoException;
import Adimin_Cond.core.exception.visitante.DocumentoRepetidoException;
import Adimin_Cond.core.exception.visitante.VisitaJaEmAndamentoException;
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

import java.time.LocalDateTime;

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

        if(this.visitanteRepository.existsByDocumento(visitaDTO.documento()))
        {
            throw new DocumentoRepetidoException();
        }

        boolean visitaAtiva = this.visitanteRepository.existsByDocumentoAndStatusVisitante(visitaDTO.documento(),StatusVisitante.EM_VISITA);
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
        Morador morador = this.moradorRepository.findById(visitaDTO.idMorador()).orElseThrow(()->new IdNaoEncontradoException("Id de morador não encontrado"));

        Visitante acessoAbertoVisitante = this.visitanteRepository.findByMoradorAndDataSaidaIsNull(morador);
        if(acessoAbertoVisitante == null)
        {
            throw new AcessoRestritoException("Visitante sem registro de entrada");
        }

        acessoAbertoVisitante.setNome(visitaDTO.nome());
        acessoAbertoVisitante.setDocumento(visitaDTO.documento());
        acessoAbertoVisitante.setMorador(morador);
        acessoAbertoVisitante.setDataSaida(LocalDateTime.now());
        acessoAbertoVisitante.setStatusVisitante(StatusVisitante.FINALIZADA);

        this.visitanteRepository.save(acessoAbertoVisitante);

        return VisitanteResponseDTO.fromVisitante(acessoAbertoVisitante);
    }
}
