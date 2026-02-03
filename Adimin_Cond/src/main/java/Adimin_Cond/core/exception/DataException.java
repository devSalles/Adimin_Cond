package Adimin_Cond.core.exception;

public class DataException extends RuntimeException {
    public DataException(String message) {
        super(message);
    }
    public DataException() {
        super("Data final não pode ser maior que data inicial");
    }
}
