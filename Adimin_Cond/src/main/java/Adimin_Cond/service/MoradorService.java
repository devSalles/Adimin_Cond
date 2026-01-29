package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.core.exception.*;
import Adimin_Cond.dto.morador.MoradorRequestDTO;
import Adimin_Cond.dto.morador.MoradorResponseDTO;
import Adimin_Cond.entity.*;
import Adimin_Cond.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MoradorService {

    private final MoradorRepository moradorRepository;
    private final ApartamentoRepository apartamentoRepository;
    private final VeiculoRepository veiculoRepository;
    private final TaxaCondominioRepository taxaCondominioRepository;
    private final VisitanteRepository visitanteRepository;

    @Transactional
    public MoradorResponseDTO salvar(MoradorRequestDTO dto)
    {
        if(this.moradorRepository.existsByEmail(dto.email()))
        {
            throw new EmailRepetidoException();
        }

        if(this.moradorRepository.existsByCpf(dto.cpf()))
        {
            throw new CpfRepetidoException();
        }

        if(this.moradorRepository.existsByTelefone(dto.telefone()))
        {
            throw new TelefoneRepetidoException();
        }

        Morador morador = dto.toMorador();
        morador.setStatus(StatusMorador.ATIVO);
        this.moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);

    }

    @Transactional
    public MoradorResponseDTO vincularApartamento(Long moradorId, Long apartamentoId) {
        Morador morador = buscarMoradorAtivo(moradorId);
        Apartamento apartamento = apartamentoRepository.findById(apartamentoId).orElseThrow(() -> new IdNaoEncontradoException("Apartamento não encontrado"));

        morador.setApartamento(apartamento);
        moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    @Transactional
    public MoradorResponseDTO adicionarVeiculo(Long moradorId, Long veiculoId) {
        Morador morador = buscarMoradorAtivo(moradorId);
        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElseThrow(() -> new IdNaoEncontradoException("Veículo não encontrado"));

        morador.getVeiculos().add(veiculo);
        moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    @Transactional
    public MoradorResponseDTO adicionarVisitante(Long moradorId, Long visitanteId) {
        Morador morador = buscarMoradorAtivo(moradorId);
        Visitante visitante = visitanteRepository.findById(visitanteId).orElseThrow(() -> new IdNaoEncontradoException("Visitante não encontrado"));

        morador.getVisitantes().add(visitante);
        moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    @Transactional
    public MoradorResponseDTO adicionarTaxaCondominio(Long moradorId, Long taxaId) {
        Morador morador = buscarMoradorAtivo(moradorId);
        TaxaCondominio taxa = taxaCondominioRepository.findById(taxaId).orElseThrow(() -> new IdNaoEncontradoException("Taxa não encontrada"));

        morador.getTaxaCondominio().add(taxa);
        moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    private Morador buscarMoradorAtivo(Long id) {
        Morador morador = moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));

        if (morador.getStatus() == StatusMorador.INATIVO) {
            throw new MoradorInativoException();
        }

        return morador;
    }
}
