package Adimin_Cond.core.exception.apartamento;

public class ApartamentoVinculadoException extends RuntimeException {
    public ApartamentoVinculadoException(String message) {
        super(message);
    }
    public ApartamentoVinculadoException() {
        super("Apartamento vinculado não pode ser desativado");
    }
}
