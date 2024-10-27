package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//encripta y desencripta las contraseñas mediante hash
//no es tan seguro pero se tiene cierta seguridad, lo mejor seria usar alguna libreria como BCrypt 
//pero a fines practicos sirve para no guardar la contraseña de manera visible en la base de datos
public class PasswordUtils {

    // Genera un hash SHA-256 para una contraseña proporcionada
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar la contraseña", e);
        }
    }

    // verifica si una contraseña en texto plano coincide con un hash encriptado
    public static boolean checkPassword(String password, String hashedPassword) {
        return hashPassword(password).equals(hashedPassword);
    }
}
