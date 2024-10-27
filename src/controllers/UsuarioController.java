package controllers;

import models.Usuario;
import services.UsuarioService;

public class UsuarioController extends AbstractGenericController<Usuario, Integer> {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected UsuarioService getService() {
        return usuarioService;
    }

    // busca el usuario logueado actualmente
    public Usuario getUsuarioLogueado() {
        return UsuarioService.getUsuarioActual();
    }

}
