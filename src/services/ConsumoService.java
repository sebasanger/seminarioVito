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

//de las entidades principales o complejas
//maneja los consumos realizados durante la estadia del huesped
public class ConsumoService extends AbstractGenericService<Consumicion, Integer> {

    private ConsumicionRepository consumicionRepository = new ConsumicionRepository();

    // se inicializan los repositories necesarios para manejar las entidades
    // relacionadas
    ProductoRepository productoRepository = new ProductoRepository();
    ReservaRepository reservaRepository = new ReservaRepository();
    UsuarioRepository usuarioRepository = new UsuarioRepository();
    MarcaRepository marcaRepository = new MarcaRepository();

    @Override
    protected ConsumicionRepository getRepository() {
        return consumicionRepository;
    }

    // al buscar el listado de consumiciones se rellena el objeto buscnado su
    // descripcion en la base de datos
    // se mejora haciendo inner join como esta en reservas, de esta manera es mas
    // legible y se deja de esta manera para tener varias maneras de resolver el
    // mismo problema
    @Override
    public List<Consumicion> obtenerTodos() throws SQLException {
        List<Consumicion> consumiciones = this.getRepository().obtenerTodos();

        // se recorre cada consumicion y se carga con sus datos asociados como usuario
        // que genero el consumo producto en cuestion que se consumio, etc
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
        // setea la fecha actual en la consumicion
        Date fechaActual = new Date(new java.util.Date().getTime());
        consumicion.setFecha(fechaActual);

        // calcula el precio total para tenerlo contabilizado en la base de datos
        consumicion.setPrecioTotal(consumicion.getProducto().getPrecio() * consumicion.getCantidad());

        // evalua que no se haya pedido mas productos de los que se tiene en stock o
        // tira una excepcion
        if (consumicion.getCantidad() > consumicion.getProducto().getStock()) {
            throw new StockInsuficienteException();
        }
        consumicionRepository.crear(consumicion);
    }

    // busca todos los consumos realizadas en una reserva con su detalle
    public List<Consumicion> obtenerConsumosReserva(Integer reservaId) throws SQLException {
        List<Consumicion> consumiciones = this.getRepository().obtenerConsumosReserva(reservaId);

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

}