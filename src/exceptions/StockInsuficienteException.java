package exceptions;

//Exception de que no se permite la compra por falta de stock
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException() {
        String message = "Stock insuficiente del producto.";
        System.out.println(message);
        super(message);
    }
}
