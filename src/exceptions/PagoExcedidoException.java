package exceptions;

//Exception para se exede el pago pretendido
public class PagoExcedidoException extends Exception {
    public PagoExcedidoException() {
        String message = "No se permite la operacion porque el pago excede la deuda.";
        System.out.println(message);
        super(message);
    }
}
