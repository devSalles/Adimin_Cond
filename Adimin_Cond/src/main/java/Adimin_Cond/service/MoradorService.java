package Adimin_Cond.service;

import Adimin_Cond.Enum.StatusMorador;
import Adimin_Cond.Enum.StatusVisitante;
import Adimin_Cond.core.exception.*;
import Adimin_Cond.core.exception.morador.*;
import Adimin_Cond.dto.morador.MoradorRequestDTO;
import Adimin_Cond.dto.morador.MoradorResponseDTO;
import Adimin_Cond.dto.morador.MoradorUpdateRequestDTO;
import Adimin_Cond.entity.*;
import Adimin_Cond.repository.*;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MoradorService {

    private final MoradorRepository moradorRepository;
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

        String cpfFormatado = formatarCpf(dto.cpf());

        Morador morador = dto.toMorador();
        morador.setStatus(StatusMorador.ATIVO);
        morador.setCpf(cpfFormatado);

        this.moradorRepository.save(morador);

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

        if(this.moradorRepository.existsByEmail(dto.email()))
        {
            throw new EmailRepetidoException();
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

    public List<MoradorResponseDTO> buscarNomeMorador(String nome)
    {
        List<Morador> morador = this.moradorRepository.findByNome(nome);

        if(morador == null)
        {
            throw new NenhumCadastroException("Nome não encontrado");
        }

        return morador.stream().map(MoradorResponseDTO::fromMorador).toList();
    }

    public MoradorResponseDTO buscarCPFMorador(String cpf)
    {
        String cpfLimpo = cpf.replaceAll("\\D","");
        Morador morador = this.moradorRepository.findByCpf(cpfLimpo);

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

    public List<MoradorResponseDTO> buscarPorStatus(StatusMorador statusMorador)
    {
        List<Morador> moradores = this.moradorRepository.findByStatus(statusMorador);

        if(moradores.isEmpty())
        {
            throw new NenhumCadastroException("Nenhum morador cadastrado no sistema com esse status");
        }

        return moradores.stream().map(MoradorResponseDTO::fromMorador).toList();
    }

    public MoradorResponseDTO buscarID(Long id)
    {
        Morador morador = this.moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));
        return MoradorResponseDTO.fromMorador(morador);
    }

    //Exclusão via Soft Delete
    public MoradorResponseDTO desativarMorador(Long id)
    {
        Morador morador = this.moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));

        if(morador.getStatus() == StatusMorador.INATIVO)
        {
            throw new MoradorInativoException("Morador já está inativo");
        }

        boolean visitanteAtivo = this.visitanteRepository.existsByMoradorIdAndStatusVisitante(id, StatusVisitante.EM_VISITA);
        if(visitanteAtivo)
        {
            throw new VisitaAtivaException();
        }

        morador.setStatus(StatusMorador.INATIVO);
        this.moradorRepository.save(morador);

        return MoradorResponseDTO.fromMorador(morador);
    }

    //--------------------- METODOS AUXILIARES ---------------------

    @PrePersist
    private String formatarCpf(String cpf)
    {
        return cpf.replaceAll("\\D","");
    }

    private Morador buscarMorador(Long id) {
        Morador morador = moradorRepository.findById(id).orElseThrow(() -> new IdNaoEncontradoException("Morador não encontrado"));

        if (morador.getStatus() == StatusMorador.INATIVO) {
            throw new MoradorInativoException();
        }

        return morador;
    }
}
