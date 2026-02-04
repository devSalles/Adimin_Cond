package Adimin_Cond.core.exception.acesso;

public class AcessoRestritoException extends RuntimeException {
    public AcessoRestritoException(String message) {
        super(message);
    }
    public AcessoRestritoException() {
        super("Veículo possui entrada registrada sem saída");
    }
}
