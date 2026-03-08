package Adimin_Cond.core.exception.morador;

public class EmailNaoEncontradoException extends RuntimeException {
    public EmailNaoEncontradoException(String message) {
        super(message);
    }
    public EmailNaoEncontradoException() {
        super("Email não encontrado");
    }

}

