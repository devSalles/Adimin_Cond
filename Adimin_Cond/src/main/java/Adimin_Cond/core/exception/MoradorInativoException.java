package Adimin_Cond.core.exception;

public class MoradorInativoException extends RuntimeException {
    public MoradorInativoException(String message) {
        super(message);
    }
    public MoradorInativoException() {
        super("Morador inativo não pode ser vinculado");
    }
}
