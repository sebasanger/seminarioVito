package exceptions;

//Exception para cuando estan mal las credenciales
public class CredencialesInvalidasException extends Exception {
    public CredencialesInvalidasException() {
        String message = "Credenciales invalidas.";
        System.out.println(message);
        super(message);
    }
}
