package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusVeiculo;
import Adimin_Cond.core.exception.IdNaoEncontradoException;
import Adimin_Cond.core.exception.NenhumCadastroException;
import Adimin_Cond.core.exception.PlacaRepetidaException;
import Adimin_Cond.core.exception.VeiculoInativoException;
import Adimin_Cond.dto.veiculo.VeiculoRequestDTO;
import Adimin_Cond.dto.veiculo.VeiculoResponseDTO;
import Adimin_Cond.dto.veiculo.VeiculoUpdateRequestDTO;
import Adimin_Cond.entity.Morador;
import Adimin_Cond.entity.Veiculo;
import Adimin_Cond.repository.MoradorRepository;
import Adimin_Cond.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final MoradorRepository moradorRepository;
    private final VeiculoRepository veiculoRepository;

    @Transactional
    public VeiculoResponseDTO salvarVeiculo(VeiculoRequestDTO veiculoDTO)
    {
        Morador morador = this.moradorRepository.findById(veiculoDTO.idMorador()).orElseThrow(()->new IdNaoEncontradoException("ID de Morador não encontrado"));;
        Veiculo veiculo = veiculoDTO.toVeiculo(morador);

        if(this.veiculoRepository.existsByPlaca(veiculoDTO.placa()))
        {
            throw new PlacaRepetidaException("Placa já está cadastrada");
        }

        veiculo.setPlaca(limparPlaca(veiculoDTO.placa()));
        veiculo.setStatus(StatusVeiculo.ATIVO);
        this.veiculoRepository.save(veiculo);

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    @Transactional
    public VeiculoResponseDTO atualizarVeiculo(Long id, VeiculoUpdateRequestDTO veiculoUpdateDTO)
    {
        Veiculo veiculo = this.veiculoRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de veiculo não encontrado"));
        veiculoUpdateDTO.updateVeiculo(veiculo);

        this.veiculoRepository.save(veiculo);

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    public VeiculoResponseDTO buscarPlaca(String placa)
    {
        Veiculo veiculo = this.veiculoRepository.findByPlaca(placa);

        if(veiculo == null)
        {
            throw new PlacaRepetidaException("Placa não encontrada");
        }

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    public List<VeiculoResponseDTO> listarTodos()
    {
        List<Veiculo> veiculos = this.veiculoRepository.findAll();

        if(veiculos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum veículo cadastrado");
        }

        return veiculos.stream().map(VeiculoResponseDTO::fromVeiculo).toList();
    }

    public List<VeiculoResponseDTO> listarPorStatus(StatusVeiculo statusVeiculo)
    {
        List<Veiculo> veiculos = this.veiculoRepository.findByStatus(statusVeiculo);

        if(veiculos.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum veículo com esse status");
        }

        return veiculos.stream().map(VeiculoResponseDTO::fromVeiculo).toList();
    }

    public VeiculoResponseDTO veiculoID(Long id)
    {
        Veiculo veiculo = this.veiculoRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de veiculo não encontrado"));
        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    //Exclusão via Soft Delete
    public VeiculoResponseDTO desativarVeiculo(Long id)
    {
        Veiculo veiculo = this.veiculoRepository.findById(id).orElseThrow(()->new IdNaoEncontradoException("ID de veiculo não encontrado"));

        if(veiculo.getStatus() == StatusVeiculo.INATIVO)
        {
            throw new VeiculoInativoException("Veículo já está inativo");
        }

        veiculo.setStatus(StatusVeiculo.INATIVO);

        this.veiculoRepository.save(veiculo);

        return VeiculoResponseDTO.fromVeiculo(veiculo);
    }

    // //------------ METODOS AUXILIARES ------------

    public String limparPlaca(String placa)
    {
        return placa.replaceAll("[^A-Za-z0-9]","").toUpperCase();
    }
}
