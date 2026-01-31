package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusApartamento;
import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.core.exception.*;
import Adimin_Cond.dto.apartamento.ApartamentoRequestDTO;
import Adimin_Cond.dto.apartamento.ApartamentoResponseDTO;
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
    public VincularAptoResponseDTO vincularApartamento(Long IdApartamento, Long IdMorador)
    {
        Apartamento apto = buscarID(IdApartamento);
        Morador morador = this.moradorRepository.findById(IdMorador).orElseThrow(()->new IdNaoEncontradoException("ID de morador não encontrado"));

        //Validações para vínculo
        validarApartamento(apto);
        validarMorador(morador);

        morador.setApartamento(apto);
        apto.setMorador(morador);

        apto.setStatusApartamento(StatusApartamento.OCUPADO);

        this.apartamentoRepository.save(apto);

        return VincularAptoResponseDTO.fromApartamento(apto);
    }

    @Transactional
    public ApartamentoResponseDTO desvincularApartamento(Long idApartamento)
    {
        Apartamento apto = buscarID(idApartamento);

        if(apto.getMorador() == null)
        {
            throw new MoradorDesvinculadoException("Morador já está desvinculado");
        }

        Morador morador = apto.getMorador();

        apto.setMorador(null);
        morador.setApartamento(null);

        apto.setStatusApartamento(StatusApartamento.DESOCUPADO);

        this.moradorRepository.save(morador);
        this.apartamentoRepository.save(apto);

        return ApartamentoResponseDTO.fromApartamento(apto);
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

    public void validarApartamento(Apartamento apartamento)
    {
        if(apartamento.getStatusApartamento() == StatusApartamento.INATIVO ||
           apartamento.getStatusApartamento() == StatusApartamento.MANUTENCAO ||
           apartamento.getStatusApartamento() == StatusApartamento.OCUPADO)
        {
            throw new AptoIndisponivelException("Apartamento indisponível para vínculo");
        }

        if(apartamento.getMorador()!= null)
        {
            throw new MoradorJaVinculadoException("Apartamento já possui morador");
        }

    }

    public void validarMorador(Morador morador)
    {
        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException("Morador inativo não pode ser vinculado");
        }

        if(morador.getApartamento() != null)
        {
            throw new MoradorJaVinculadoException("Morador já está vinculado a um apartamento");
        }

    }
}