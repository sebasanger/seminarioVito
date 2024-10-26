package services;

import models.Usuario;
import repositories.UsuarioRepository;

public class UsuarioService extends AbstractGenericService<Usuario, Integer> {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    @Override
    protected UsuarioRepository getRepository() {
        return usuarioRepository;
    }

}