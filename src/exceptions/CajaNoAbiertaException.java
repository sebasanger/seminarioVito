package exceptions;

public class CajaNoAbiertaException extends Exception {
    public CajaNoAbiertaException() {
        String message = "No se encontro caja abierta, por favor abrir una antes de realizar esta funcion";
        System.out.println(message);
        super(message);
    }
}
