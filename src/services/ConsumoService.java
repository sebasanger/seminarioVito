package services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import exceptions.StockInsuficienteException;
import models.Consumicion;
import repositories.ConsumicionRepository;
import repositories.MarcaRepository;
import repositories.ProductoRepository;
import repositories.ReservaRepository;
import repositories.UsuarioRepository;

public class ConsumoService extends AbstractGenericService<Consumicion, Integer> {
    private ConsumicionRepository consumicionRepository = new ConsumicionRepository();

    @Override
    protected ConsumicionRepository getRepository() {
        return consumicionRepository;
    }

    @Override
    public List<Consumicion> obtenerTodos() throws SQLException {
        ProductoRepository productoRepository = new ProductoRepository();
        ReservaRepository reservaRepository = new ReservaRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        MarcaRepository marcaRepository = new MarcaRepository();

        List<Consumicion> consumiciones = this.getRepository().obtenerTodos();

        for (Consumicion consumicion : consumiciones) {
            consumicion.setProducto(productoRepository.obtenerPorId(consumicion.getProducto().getId()));
            consumicion.setReserva(reservaRepository.obtenerPorId(consumicion.getReserva().getId()));
            consumicion.setUsuario(usuarioRepository.obtenerPorId(consumicion.getUsuario().getId()));
            consumicion.getProducto()
                    .setMarca(marcaRepository.obtenerPorId(consumicion.getProducto().getMarca().getId()));
            consumicion.setUsuario(usuarioRepository.obtenerPorId(consumicion.getUsuario().getId()));

        }

        return consumiciones;
    }

    public void crearConsumo(Consumicion consumicion) throws SQLException, StockInsuficienteException {
        Date fechaActual = new Date(new java.util.Date().getTime());
        consumicion.setFecha(fechaActual);

        consumicion.setPrecioTotal(consumicion.getProducto().getPrecio() * consumicion.getCantidad());

        if (consumicion.getCantidad() > consumicion.getProducto().getStock()) {
            throw new StockInsuficienteException();
        }

        consumicionRepository.crear(consumicion);

    }

}