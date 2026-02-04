package Adimin_Cond.core.exception.veiculo;

public class PlacaRepetidaException extends RuntimeException {
    public PlacaRepetidaException(String message) {
        super(message);
    }
    public PlacaRepetidaException() {
        super("");
    }
}
