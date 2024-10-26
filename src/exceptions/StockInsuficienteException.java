package exceptions;

public class StockInsuficienteException extends Exception {
    public StockInsuficienteException() {
        String message = "Stock insuficiente del producto.";
        System.out.println(message);
        super(message);
    }
}
