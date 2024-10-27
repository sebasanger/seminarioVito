package services;

import java.sql.SQLException;

import exceptions.CredencialesInvalidasException;
import models.Usuario;
import repositories.UsuarioRepository;
import utils.PasswordUtils;

//maneja el personal y usaurio que se loguea a la aplicacion
public class UsuarioService extends AbstractGenericService<Usuario, Integer> {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static Usuario usuarioActual;

    @Override
    protected UsuarioRepository getRepository() {
        return usuarioRepository;
    }

    @Override
    public void crear(Usuario usuario) throws SQLException {
        // encripta la password del usuario
        String passwordSinEncriptar = usuario.getPassword();
        String passwordEncriptada = PasswordUtils.hashPassword(passwordSinEncriptar);

        usuario.setPassword(passwordEncriptada);

        usuarioRepository.crear(usuario);
    }

    // evalua si el email y password coinciden con alguno en la base de datos
    public Usuario loginUsuario(String email, String password) throws SQLException, CredencialesInvalidasException {

        // primero obtiene el usuario por su mail
        Usuario usuario = usuarioRepository.obtenerPorEmail(email);

        // si ya no es encontrado de una SQLException con un mensaje
        if (usuario == null) {
            throw new SQLException("Usuario no encontrado");
        }

        // si se encuentra el usuario se evalua su password
        if (PasswordUtils.checkPassword(password, usuario.getPassword())) {
            // al dar correctamente la validacion se setea el usuario loegueado con el
            // resultado de la base de datos
            UsuarioService.usuarioActual = usuario;
            return usuario;
        } else {
            throw new CredencialesInvalidasException();
        }

    }

    // obtiene los datos del usuario loegueado actualemtne
    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

}