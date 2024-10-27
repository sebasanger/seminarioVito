package controllers;

import models.Usuario;
import services.UsuarioService;

public class UsuarioController extends AbstractGenericController<Usuario, Integer> {

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected UsuarioService getService() {
        return usuarioService;
    }

    public Usuario getUsuarioLogueado() {
        return UsuarioService.getUsuarioActual();
    }

}
