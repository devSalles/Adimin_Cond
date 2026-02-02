package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.Enum.TipoAcesso;
import Adimin_Cond.core.exception.AcessoRestritoException;
import Adimin_Cond.core.exception.IdNaoEncontradoException;
import Adimin_Cond.core.exception.MoradorInativoException;
import Adimin_Cond.core.exception.VeiculoInativoException;
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

import java.time.LocalDateTime;

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

        Acesso acessoAberto = this.acessoRepository.findByVeiculoAndDataHoraSaidaIsNull(veiculo);
        if(acessoAberto!=null)
        {
            throw new AcessoRestritoException("Veículo possui entrada registrada sem saída");
        }

        Acesso acesso = acessoDTO.toAcesso(veiculo);
        acesso.setDataHoraEntrada(LocalDateTime.now());
        acesso.setTipo(TipoAcesso.ENTRADA);

        this.acessoRepository.save(acesso);

        return AcessoResponseDTO.fromAcesso(acesso);
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

        acessoAberto.setTipo(TipoAcesso.SAIDA);
        acessoAberto.setDataHoraSaida(LocalDateTime.now());

        if(acessoDTO.porteiro() != null && !acessoDTO.porteiro().isBlank()) {
            acessoAberto.setPorteiro(acessoDTO.porteiro());
        }

        this.acessoRepository.save(acessoAberto);

        return AcessoResponseDTO.fromAcesso(acessoAberto);
    }
}
