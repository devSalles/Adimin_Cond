package Adimin_Cond.core.exception.veiculo;

public class PlacaNaoEncontradaException extends RuntimeException {
    public PlacaNaoEncontradaException(String message) {
        super(message);
    }
    public PlacaNaoEncontradaException() {
        super("Placa não encontrada");
    }
}
