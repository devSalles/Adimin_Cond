package Adimin_Cond.core.exception;

public class PeriodoNaoEncontradoException extends RuntimeException {
    public PeriodoNaoEncontradoException(String message) {
        super(message);
    }
    public PeriodoNaoEncontradoException() {
        super("Periodo desejado não foi encontrado taxas");
    }
}
