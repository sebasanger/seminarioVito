package exceptions;

public class PagoFaltanteException extends Exception {
    public PagoFaltanteException() {
        String message = "No se permite la operacion por faltante de pagos .";
        System.out.println(message);
        super(message);
    }
}