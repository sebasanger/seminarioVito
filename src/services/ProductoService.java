package services;

import java.sql.SQLException;
import java.util.List;

import models.Producto;
import repositories.ProductoRepository;

public class ProductoService extends AbstractGenericService<Producto, Integer> {
    private ProductoRepository productoRepository = new ProductoRepository();

    @Override
    protected ProductoRepository getRepository() {
        return productoRepository;
    }

    // busca los detalles de los productos en su obtencion
    @Override
    public List<Producto> obtenerTodos() throws SQLException {
        CategoriaService categoriaService = new CategoriaService();
        MarcaService marcaService = new MarcaService();

        List<Producto> productos = this.getRepository().obtenerTodos();

        for (Producto producto : productos) {
            producto.setCategoria(categoriaService.obtenerPorId(producto.getCategoria().getId()));
            producto.setMarca(marcaService.obtenerPorId(producto.getMarca().getId()));
        }

        return productos;
    }

}