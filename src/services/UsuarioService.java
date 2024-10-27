package services;

import java.sql.SQLException;

import exceptions.CredencialesInvalidasException;
import models.Usuario;
import repositories.UsuarioRepository;
import utils.PasswordUtils;

public class UsuarioService extends AbstractGenericService<Usuario, Integer> {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();
    private static Usuario usuarioActual;

    @Override
    protected UsuarioRepository getRepository() {
        return usuarioRepository;
    }

    @Override
    public void crear(Usuario usuario) throws SQLException {
        String passwordSinEncriptar = usuario.getPassword();
        String passwordEncriptada = PasswordUtils.hashPassword(passwordSinEncriptar);

        usuario.setPassword(passwordEncriptada);

        usuarioRepository.crear(usuario);
    }

    public Usuario loginUsuario(String email, String password) throws SQLException, CredencialesInvalidasException {

        Usuario usuario = usuarioRepository.obtenerPorEmail(email);

        if (usuario == null) {
            throw new SQLException("Usuario no encontrado");
        }

        if (PasswordUtils.checkPassword(password, usuario.getPassword())) {
            UsuarioService.usuarioActual = usuario;
            return usuario;
        } else {
            throw new CredencialesInvalidasException();
        }

    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void setUsuarioActual(Usuario usuarioActual) {
        UsuarioService.usuarioActual = usuarioActual;
    }

}