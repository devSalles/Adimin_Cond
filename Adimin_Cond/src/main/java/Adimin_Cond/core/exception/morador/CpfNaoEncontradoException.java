package Adimin_Cond.core.exception.morador;

public class CpfNaoEncontradoException extends RuntimeException {
    public CpfNaoEncontradoException(String message) {
        super(message);
    }
    public CpfNaoEncontradoException() {
        super("CPF não encontrado");
    }
}
