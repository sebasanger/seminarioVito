package services;

import models.Producto;
import repositories.ProductoRepository;

public class ProductoService extends AbstractGenericService<Producto, Integer> {
    private ProductoRepository productoRepository = new ProductoRepository();

    @Override
    protected ProductoRepository getRepository() {
        return productoRepository;
    }

}