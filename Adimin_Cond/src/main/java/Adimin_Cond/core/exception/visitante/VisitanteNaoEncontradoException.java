package Adimin_Cond.core.exception.visitante;

public class VisitanteNaoEncontradoException extends RuntimeException {
    public VisitanteNaoEncontradoException(String message) {
        super(message);
    }
    public VisitanteNaoEncontradoException() {
        super("CPF de visitante não encontrado");
    }
}
