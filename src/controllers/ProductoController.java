package controllers;

import models.Producto;
import services.ProductoService;

public class ProductoController extends AbstractGenericController<Producto, Integer> {

    private ProductoService productoService = new ProductoService();

    @Override
    protected ProductoService getService() {
        return productoService;
    }

}
