package Adimin_Cond.core.exception.visitante;

public class NenhumVisitanteComStatusException extends RuntimeException {
    public NenhumVisitanteComStatusException(String message) {
        super(message);
    }
    public NenhumVisitanteComStatusException() {
        super("Nenhum visitante com esse status");
    }
}
