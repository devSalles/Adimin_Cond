package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.core.exception.*;
import Adimin_Cond.dto.morador.MoradorRequestDTO;
import Adimin_Cond.dto.morador.MoradorResponseDTO;
import Adimin_Cond.dto.morador.MoradorUpdateRequestDTO;
import Adimin_Cond.entity.*;
import Adimin_Cond.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public MoradorResponseDTO adicionarVeiculo(Long moradorId, Long veiculoId) {

        Morador morador = buscarMorador(moradorId);
        Veiculo veiculo = veiculoRepository.findById(veiculoId).orElseThrow(() -> new IdNaoEncontradoException("Veículo não encontrado"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException();
        }

        morador.getVeiculos().add(veiculo);
        moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    @Transactional
    public MoradorResponseDTO adicionarVisitante(Long moradorId, Long visitanteId) {
        Morador morador = buscarMorador(moradorId);
        Visitante visitante = visitanteRepository.findById(visitanteId).orElseThrow(() -> new IdNaoEncontradoException("Visitante não encontrado"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException();
        }

        morador.getVisitantes().add(visitante);
        moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    @Transactional
    public MoradorResponseDTO adicionarTaxaCondominio(Long moradorId, Long taxaId) {

        Morador morador = buscarMorador(moradorId);
        TaxaCondominio taxa = taxaCondominioRepository.findById(taxaId).orElseThrow(() -> new IdNaoEncontradoException("Taxa não encontrada"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException();
        }

        morador.getTaxaCondominio().add(taxa);
        moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    @Transactional
    public MoradorResponseDTO atualizarMorador(Long id,MoradorUpdateRequestDTO dto)
    {
        Morador morador = moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException("Morador está com status inativo");
        }

        dto.updateMorador(morador);
        this.moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    public List<MoradorResponseDTO> listarTodos()
    {
        List<Morador>moradores = this.moradorRepository.findAll();

        if(moradores.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum morador cadastrado no sistema");
        }

        return moradores.stream().map(MoradorResponseDTO::fromMorador).toList();
    }

    public MoradorResponseDTO buscarNomeMorador(String nome)
    {
        Morador morador = this.moradorRepository.findByNome(nome);

        if(morador == null)
        {
            throw new NenhumCadastroException("Nome não encontrado");
        }

        return MoradorResponseDTO.fromMorador(morador);
    }

    public MoradorResponseDTO buscarCPFMorador(String cpf)
    {
        Morador morador = this.moradorRepository.findByCpf(cpf);

        if(morador == null)
        {
            throw new NenhumCadastroException("CPF não encontrado");
        }

        return MoradorResponseDTO.fromMorador(morador);
    }

    public MoradorResponseDTO buscarEmailMorador(String email)
    {
        Morador morador = this.moradorRepository.findByEmail(email);

        if(morador == null)
        {
            throw new NenhumCadastroException("Email não encontrado");
        }

        return MoradorResponseDTO.fromMorador(morador);
    }

    public MoradorResponseDTO buscarID(Long id)
    {
        Morador morador = this.moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));
        return MoradorResponseDTO.fromMorador(morador);
    }

    public MoradorResponseDTO desativarMorador(Long id)
    {
        Morador morador = this.moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException("Morador já está inativo");
        }

        boolean visitanteAtivo = this.moradorRepository.existsByVisitantesAndStatusIn(id, List.of(StatusVisitante.EM_VISITA));
        if(visitanteAtivo)
        {
            throw new VisitaAtivaException();
        }

        morador.setStatus(StatusMorador.INATIVO);
        this.moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    //--------------------- METODOS AUXILIARES ---------------------

    private Morador buscarMorador(Long id) {
        Morador morador = moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));

        if (morador.getStatus() == StatusMorador.INATIVO) {
            throw new MoradorInativoException();
        }

        return morador;
    }
}
