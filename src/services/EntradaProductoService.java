package services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import models.EntradaProducto;
import repositories.EntradaProductosRepository;
import repositories.ProductoRepository;
import repositories.UsuarioRepository;

public class EntradaProductoService extends AbstractGenericService<EntradaProducto, Integer> {
    private EntradaProductosRepository entradaProductosRepository = new EntradaProductosRepository();
    private ProductoRepository productoRepository = new ProductoRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    @Override
    protected EntradaProductosRepository getRepository() {
        return entradaProductosRepository;
    }

    @Override
    public List<EntradaProducto> obtenerTodos() throws SQLException {
        List<EntradaProducto> entradasProductos = this.getRepository().obtenerTodos();

        for (EntradaProducto entradaProducto : entradasProductos) {
            entradaProducto.setProducto(productoRepository.obtenerPorId(entradaProducto.getProducto().getId()));
            entradaProducto.setUsuario(usuarioRepository.obtenerPorId(entradaProducto.getUsuario().getId()));
        }

        return entradasProductos;
    }

    @Override
    public void crear(EntradaProducto entradaProducto) throws SQLException {
        Date fechaActual = new Date(new java.util.Date().getTime());
        entradaProducto.setFecha(fechaActual);

        entradaProductosRepository.crear(entradaProducto);

    }

}