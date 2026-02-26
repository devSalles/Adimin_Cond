package Adimin_Cond.core.exception.morador;

public class EmailNaoEcontradoException extends RuntimeException {
    public EmailNaoEcontradoException(String message) {
        super(message);
    }
    public EmailNaoEcontradoException() {
        super("Email não encontrado");
    }

}

