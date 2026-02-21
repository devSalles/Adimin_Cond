package Adimin_Cond.core.exception.visitante;

public class NomeDeVisitanteNaoEncontradoException extends RuntimeException {
    public NomeDeVisitanteNaoEncontradoException(String message) {
        super(message);
    }
    public NomeDeVisitanteNaoEncontradoException() {
        super("Nome de visitante não encontrado");
    }
}
