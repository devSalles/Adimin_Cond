package Adimin_Cond.core.exception.visitante;

public class DocumentoRepetidoException extends RuntimeException {
    public DocumentoRepetidoException(String message) {
        super(message);
    }
    public DocumentoRepetidoException() {
        super("Documento já cadastrado no sistema");
    }
}
