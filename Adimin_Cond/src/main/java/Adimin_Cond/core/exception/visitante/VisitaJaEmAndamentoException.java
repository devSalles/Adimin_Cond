package Adimin_Cond.core.exception.visitante;

public class VisitaJaEmAndamentoException extends RuntimeException {
    public VisitaJaEmAndamentoException(String message) {
        super(message);
    }
    public VisitaJaEmAndamentoException() {
        super("Visitante já possui uma visita ativa");
    }
}
