package Adimin_Cond.core.exception;

public class VisitaAtivaException extends RuntimeException {
    public VisitaAtivaException(String message) {
        super(message);
    }
    public VisitaAtivaException() {
        super("Morador não pode ser excluido pois está com visita ativa");
    }
}
