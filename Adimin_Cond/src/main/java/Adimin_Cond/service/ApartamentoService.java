package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.core.exception.*;
import Adimin_Cond.dto.apartamento.ApartamentoRequestDTO;
import Adimin_Cond.dto.apartamento.ApartamentoResponseDTO;
import Adimin_Cond.dto.apartamento.VincularAptoRequestDTO;
import Adimin_Cond.dto.apartamento.VincularAptoResponseDTO;
import Adimin_Cond.entity.Apartamento;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.repository.ApartamentoRepository;
import Adimin_Cond.repository.MoradorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ApartamentoService {

    private final MoradorRepository moradorRepository;
    private final ApartamentoRepository apartamentoRepository;

    @Transactional
    public ApartamentoResponseDTO salvar(ApartamentoRequestDTO dto)
    {
        Apartamento apartamento = dto.toApartamento();
        apartamento.setStatusApartamento(StatusApartamento.DESOCUPADO);

        this.apartamentoRepository.save(apartamento);
        return ApartamentoResponseDTO.fromApartamento(apartamento);
    }

    @Transactional
    public ApartamentoResponseDTO atualizarApartamento(Long id, ApartamentoRequestDTO dto)
    {
        Apartamento apto = buscarID(id);

        Apartamento aptoAtualizado = dto.updateApartamento(apto);
        this.apartamentoRepository.save(aptoAtualizado);

        return ApartamentoResponseDTO.fromApartamento(aptoAtualizado);
    }

    @Transactional
    public VincularAptoResponseDTO vincularApartamento(VincularAptoRequestDTO dto)
    {
        Apartamento apto = buscarID(dto.IdApartamento());
        Morador morador = this.moradorRepository.findById(dto.IdMorador()).orElseThrow(()->new IdNaoEncontradoException("ID de morador não encontrado"));

        boolean apartamentoIndisponivel = this.apartamentoRepository.existsByIdAndStatusApartamentoIn(dto.IdApartamento(), List.of(
                StatusApartamento.OCUPADO,StatusApartamento.MANUTENCAO));
        if(apartamentoIndisponivel)
        {
            throw new AptoIndisponivelException("Apartamento indisponível para vínculo");
        }

        if(morador.getApartamento() != null)
        {
            throw new MoradorJaVinculadoException("Morador já está vinculado a um apartamento");
        }

        morador.setApartamento(apto);
        this.apartamentoRepository.save(apto);

        return VincularAptoResponseDTO.fromApartamento(apto);
    }

    @Transactional
    public ApartamentoResponseDTO colocarEmManutencao(Long id)
    {
        Apartamento apto = buscarID(id);
        if(apto.getStatusApartamento() == StatusApartamento.MANUTENCAO)
        {
            throw new ManutencaoException("O apartamento já está em manutenção");
        }

        apto.setStatusApartamento(StatusApartamento.MANUTENCAO);
        this.apartamentoRepository.save(apto);

        return ApartamentoResponseDTO.fromApartamento(apto);
    }

    @Transactional
    public ApartamentoResponseDTO retirarDaManutencao(Long id)
    {
        Apartamento apto = buscarID(id);

        if(apto.getStatusApartamento() != StatusApartamento.MANUTENCAO)
        {
            throw new ManutencaoException("O apartamento já está fora de manutenção");
        }

        apto.setStatusApartamento(StatusApartamento.DESOCUPADO);
        this.apartamentoRepository.save(apto);

        return ApartamentoResponseDTO.fromApartamento(apto);
    }

    public List<ApartamentoResponseDTO> listarTodos()
    {
        List<Apartamento>apartamentos = this.apartamentoRepository.findAll();

        if(apartamentos.isEmpty())
        {
            throw new NenhumCadastroException("Nnehum cadastro no banco de dados");
        }

        return apartamentos.stream().map(ApartamentoResponseDTO::fromApartamento).toList();
    }

    public List<ApartamentoResponseDTO> buscarPorStatus(StatusApartamento statusApartamento)
    {
        List<Apartamento>apartamentos = this.apartamentoRepository.findByStatusApartamento(statusApartamento);

        if(apartamentos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum apartamento cadastrado com esse status");
        }

        return apartamentos.stream().map(ApartamentoResponseDTO::fromApartamento).toList();
    }

    public List<ApartamentoResponseDTO> buscarPorBloco(String bloco)
    {
        List<Apartamento>apartamentos = this.apartamentoRepository.findByBloco(bloco);

        if(apartamentos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum apartamento cadastrado nesse bloco");
        }

        return apartamentos.stream().map(ApartamentoResponseDTO::fromApartamento).toList();
    }

    public ApartamentoResponseDTO desativarApto(Long id)
    {
        Apartamento apto = buscarID(id);

        if(apto.getStatusApartamento() == StatusApartamento.INATIVO)
        {
            throw new AptoIndisponivelException("Apartamento já esta inativo");
        }
        apto.setStatusApartamento(StatusApartamento.INATIVO);
        this.apartamentoRepository.save(apto);

        return ApartamentoResponseDTO.fromApartamento(apto);
    }

    //--------------------- METODOS AUXILIARES ---------------------

    public Apartamento buscarID(Long id)
    {
        return this.apartamentoRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de apartamento não encontrado"));
    }
}