package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.Enum.TipoAcesso;
import Adimin_Cond.core.exception.*;
import Adimin_Cond.core.exception.acesso.AcessoRestritoException;
import Adimin_Cond.core.exception.morador.MoradorInativoException;
import Adimin_Cond.core.exception.veiculo.VeiculoInativoException;
import Adimin_Cond.dto.acesso.AcessoEntradaRequestDTO;
import Adimin_Cond.dto.acesso.AcessoResponseDTO;
import Adimin_Cond.dto.acesso.AcessoSaidaRequestDTO;
import Adimin_Cond.entity.Acesso;
import Adimin_Cond.entity.Veiculo;
import Adimin_Cond.repository.AcessoRepository;
import Adimin_Cond.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcessoService {

    private final AcessoRepository acessoRepository;
    private final VeiculoRepository veiculoRepository;

    @Transactional
    public AcessoResponseDTO registarEntrada(AcessoEntradaRequestDTO acessoDTO)
    {
        Veiculo veiculo = this.veiculoRepository.findById(acessoDTO.veiculo()).orElseThrow(()->new IdNaoEncontradoException("ID de veículo não encontrado"));

        if(veiculo.getStatus() == StatusVeiculo.INATIVO)
        {
            throw new VeiculoInativoException("Véiculo está inativo");
        }

        if(veiculo.getMorador().getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException("Morador está inativo");
        }

        Acesso acesso = acessoDTO.toAcesso(veiculo);
        acesso.setDataHoraEntrada(LocalDateTime.now());
        acesso.setTipoAcesso(TipoAcesso.ENTRADA);

        Acesso acessoExistente = this.acessoRepository.findTopByVeiculoOrderByDataHoraEntradaDesc(veiculo).orElse(acesso);
        acessoExistente.setPorteiro(acesso.getPorteiro());
        acessoExistente.setDataHoraEntrada(acesso.getDataHoraEntrada());
        acessoExistente.setTipoAcesso(acesso.getTipoAcesso());
        acessoExistente.setDataHoraSaida(null);


        this.acessoRepository.save(acessoExistente);

        return AcessoResponseDTO.fromAcesso(acessoExistente);
    }

    @Transactional
    public AcessoResponseDTO registrarSaida(AcessoSaidaRequestDTO acessoDTO)
    {
        Veiculo veiculo = this.veiculoRepository.findById(acessoDTO.veiculoId()).orElseThrow(()->new IdNaoEncontradoException("ID de veículo não encontrado"));

        Acesso acessoAberto = this.acessoRepository.findByVeiculoAndDataHoraSaidaIsNull(veiculo);
        if(acessoAberto==null)
        {
            throw new AcessoRestritoException("Não há registro de entrada para esse veículo");
        }

        acessoAberto.setTipoAcesso(TipoAcesso.SAIDA);
        acessoAberto.setDataHoraSaida(LocalDateTime.now());

        if(acessoDTO.porteiro() != null && !acessoDTO.porteiro().isBlank()) {
            acessoAberto.setPorteiro(acessoDTO.porteiro());
        }

        this.acessoRepository.save(acessoAberto);

        return AcessoResponseDTO.fromAcesso(acessoAberto);
    }

    public List<AcessoResponseDTO> listarTodos()
    {
        List<Acesso>acesso = this.acessoRepository.findAll();

        if(acesso.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum acesso registrado no banco de dados");
        }

        return acesso.stream().map(AcessoResponseDTO::fromAcesso).toList();
    }

    public AcessoResponseDTO buscarID(Long id)
    {
        Acesso acessos = this.acessoRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de acesso não encontrado"));
        return AcessoResponseDTO.fromAcesso(acessos);
    }

    public List<AcessoResponseDTO> consultarDataHoraEntrada(LocalDate inicio, LocalDate fim)
    {
        if(fim.isBefore(inicio))
        {
            throw new DataException();
        }

        LocalDateTime inicioFormatado = inicio.atStartOfDay();
        LocalDateTime fimFormatado = fim.atTime(LocalTime.MAX);

        List<Acesso> acessos = this.acessoRepository.findByDataHoraEntradaBetween(inicioFormatado,fimFormatado);

        if(acessos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum registro cadastrado");
        }

        return acessos.stream().map(AcessoResponseDTO::fromAcesso).toList();
    }

    public List<AcessoResponseDTO> consultarDataHoraSaida(LocalDate inicio, LocalDate fim)
    {
        if(fim.isBefore(inicio))
        {
            throw new DataException();
        }

        LocalDateTime incioFormatado = inicio.atStartOfDay();
        LocalDateTime fimFormatado  = fim.atTime(LocalTime.MAX);

        List<Acesso>acessos = this.acessoRepository.findByDataHoraSaidaBetween(incioFormatado,fimFormatado);

        if(acessos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum registro cadastrado");
        }

        return acessos.stream().map(AcessoResponseDTO::fromAcesso).toList();
    }

    public List<AcessoResponseDTO> consultarTiposAcessos(TipoAcesso tipoAcesso)
    {
        List<Acesso>acessos = this.acessoRepository.findByTipoAcesso(tipoAcesso);

        if(acessos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum registro de acesso");
        }

        return acessos.stream().map(AcessoResponseDTO::fromAcesso).toList();
    }
}
