package Adimin_Cond.core.exception.morador;

public class TelefoneRepetidoException extends RuntimeException {
    public TelefoneRepetidoException(String message) {
        super(message);
    }
    public TelefoneRepetidoException() {
        super("Telefone já cadastrado no sistema");
    }
}
