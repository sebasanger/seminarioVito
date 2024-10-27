package controllers;

import models.EntradaProducto;
import services.EntradaProductoService;

public class EntradaProductoController extends AbstractGenericController<EntradaProducto, Integer> {

    private EntradaProductoService entradaProductoService = new EntradaProductoService();

    @Override
    protected EntradaProductoService getService() {
        return entradaProductoService;
    }

}
