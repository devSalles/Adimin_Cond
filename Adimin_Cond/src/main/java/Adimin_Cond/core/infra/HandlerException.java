package Adimin_Cond.core.infra;

import Adimin_Cond.core.exception.*;
import Adimin_Cond.core.exception.acesso.AcessoRestritoException;
import Adimin_Cond.core.exception.apartamento.AptoIndisponivelException;
import Adimin_Cond.core.exception.apartamento.ManutencaoException;
import Adimin_Cond.core.exception.apartamento.MoradorDesvinculadoException;
import Adimin_Cond.core.exception.apartamento.MoradorJaVinculadoException;
import Adimin_Cond.core.exception.morador.*;
import Adimin_Cond.core.exception.taxa.ReferenciaRepetidaException;
import Adimin_Cond.core.exception.taxa.TaxaJaPagaException;
import Adimin_Cond.core.exception.veiculo.PlacaNaoEncontradaException;
import Adimin_Cond.core.exception.veiculo.PlacaRepetidaException;
import Adimin_Cond.core.exception.veiculo.VeiculoInativoException;
import Adimin_Cond.core.exception.visitante.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class HandlerException {

    //Exceção global
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<MessageRestError> excecoesGlobais()
//    {
//        MessageRestError messageRestError = new MessageRestError(HttpStatus.INTERNAL_SERVER_ERROR,"Erro interno, tente novamente mais tarde");
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageRestError);
//    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<MessageRestError> MethodArgumentNotValidException(MethodArgumentNotValidException ex)
    {
        Map<String, String>errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error->errors.put(error.getField(),error.getDefaultMessage()));

        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,"Erro de validação",errors);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(NenhumCadastroException.class)
    public ResponseEntity<MessageRestError> NenhumCadastroException(NenhumCadastroException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    @ExceptionHandler(IdNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> IdNaoEncontradoException(IdNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.NOT_FOUND,ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageRestError);
    }

    // ------------ EXCEÇÕES DE APARTAMENTO ------------

    @ExceptionHandler(AptoIndisponivelException.class)
    public ResponseEntity<MessageRestError> ApartamentoIndisponivelException(AptoIndisponivelException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(ManutencaoException.class)
    public ResponseEntity<MessageRestError> AptoEmManutencaoException(ManutencaoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(MoradorDesvinculadoException.class)
    public ResponseEntity<MessageRestError> MoradorDesvinculadoException(MoradorDesvinculadoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(MoradorJaVinculadoException.class)
    public ResponseEntity<MessageRestError> MoradorJaVinculadoException(MoradorJaVinculadoException ex)
    {
        MessageRestError messageRestError = new MessageRestError();
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    //------------ EXCEÇÕES DE MORADOR ------------

    @ExceptionHandler(CpfRepetidoException.class)
    public ResponseEntity<MessageRestError> CpfRepetidoException(CpfRepetidoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(EmailRepetidoException.class)
    public ResponseEntity<MessageRestError> EmailRepetidoException(EmailRepetidoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(MoradorInativoException.class)
    public ResponseEntity<MessageRestError> MoradorInativoException(MoradorInativoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(TelefoneRepetidoException.class)
    public ResponseEntity<MessageRestError> TelefoneRepetidoException(TelefoneRepetidoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(VisitaAtivaException.class)
    public ResponseEntity<MessageRestError> VisitaAtivaException(VisitaAtivaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }
    @ExceptionHandler(CpfNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> CpfNaoEncontradoException(CpfNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(EmailNaoEcontradoException.class)
    public ResponseEntity<MessageRestError> EmailNaoEcontradoException(EmailNaoEcontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    //------------ EXCEÇÕES DE VEÍCULO ------------

    @ExceptionHandler(PlacaNaoEncontradaException.class)
    public ResponseEntity<MessageRestError> PlacaNaoEncontradaException(PlacaNaoEncontradaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(PlacaRepetidaException.class)
    public ResponseEntity<MessageRestError> PlacaRepetidaException(PlacaRepetidaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(VeiculoInativoException.class)
    public ResponseEntity<MessageRestError> VeiculoInativoException(VeiculoInativoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    //------------ EXCEÇÕES DE ACESSO ------------

    @ExceptionHandler(AcessoRestritoException.class)
    public ResponseEntity<MessageRestError> AcessoRestritoException(AcessoRestritoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    @ExceptionHandler(DataException.class)
    public ResponseEntity<MessageRestError> AcessoRestritoException(DataException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.BAD_REQUEST,ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageRestError);
    }

    //------------ EXCEÇÕES DE VISITANTE ------------

    @ExceptionHandler(DocumentoRepetidoException.class)
    public ResponseEntity<MessageRestError> DocumentoRepetidoException ( DocumentoRepetidoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(VisitaJaFinalizadaException.class)
    public ResponseEntity<MessageRestError> VisitaJaFinalizadaException(VisitaJaFinalizadaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(VisitaJaEmAndamentoException.class)
    public ResponseEntity<MessageRestError> VisitaJaEmAndamentoException(VisitaJaEmAndamentoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(VisitanteNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> VisitanteNaoEncontradoException(VisitanteNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(NomeDeVisitanteNaoEncontradoException.class)
    public ResponseEntity<MessageRestError> NomeNaoEncontradoException(NomeDeVisitanteNaoEncontradoException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }
    //------------ EXCEÇÕES DE TAXA CONDOMÍNIO ------------

    @ExceptionHandler(TaxaJaPagaException.class)
    public ResponseEntity<MessageRestError> TaxaJaPagaException(TaxaJaPagaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }

    @ExceptionHandler(ReferenciaRepetidaException.class)
    public ResponseEntity<MessageRestError> ReferenciaRepetidaException(ReferenciaRepetidaException ex)
    {
        MessageRestError messageRestError = new MessageRestError(HttpStatus.CONFLICT,ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(messageRestError);
    }
}
