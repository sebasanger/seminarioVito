package services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import exceptions.CajaNoAbiertaException;
import exceptions.PagoExcedidoException;
import models.Caja;
import models.Pago;
import models.Reserva;
import repositories.PagoRepository;
import repositories.ReservaRepository;

//clase importante, maneja los pagos de las reservas los cuales pueden ser varios
public class PagoService extends AbstractGenericService<Pago, Integer> {
    private PagoRepository pagoRepository = new PagoRepository();
    private ReservaRepository reservaRepository = new ReservaRepository();
    private CajaService cajaService = new CajaService();

    private UsuarioService usuarioService = new UsuarioService();

    @Override
    protected PagoRepository getRepository() {
        return pagoRepository;
    }

    // setea los detalles en la obtencion de los pagos
    @Override
    public List<Pago> obtenerTodos() throws SQLException {
        List<Pago> pagos = this.getRepository().obtenerTodos();
        for (Pago pago : pagos) {
            pago.setReserva(reservaRepository.obtenerPorId(pago.getReserva().getId()));
            pago.setCaja(cajaService.obtenerPorId(pago.getCaja().getId()));
            pago.setUsuario(usuarioService.obtenerPorId(pago.getUsuario().getId()));
        }
        return pagos;
    }

    public void crearPago(Pago pago) throws SQLException, PagoExcedidoException, CajaNoAbiertaException {
        // guarda la fecha acutal
        Date fechaActual = new Date(new java.util.Date().getTime());
        pago.setFecha(fechaActual);

        // busca la caja que este actualmente o da una excepcion de que no hay cajas
        // abiertas
        Caja caja = cajaService.obtenerCajaActiva();
        pago.setCaja(caja);

        Reserva reservaPagada = reservaRepository.obtenerPorId(pago.getReserva().getId());
        Double faltantePorPagar = reservaPagada.getPrecioTotal() - reservaPagada.getPagadoTotal();

        // evalua que no se pague mas de los que falta pagar o tira una excepcion
        if (pago.getCantidad() > faltantePorPagar) {
            throw new PagoExcedidoException();
        }

        // guarda el pago
        pagoRepository.crear(pago);

        // si se guardo el pago se actualizan los montos de la reserva
        Double nuevoTotalPagado = reservaPagada.getPagadoTotal() + pago.getCantidad();
        reservaPagada.setPagadoTotal(nuevoTotalPagado);
        reservaRepository.cambiarMontos(pago.getReserva().getId(), reservaPagada.getPrecioTotal(), nuevoTotalPagado);

    }

    // obtiene todos los pagos con sus detalles de una reserva
    public List<Pago> obtenerPagosReserva(Integer reservaId) throws SQLException {
        List<Pago> pagos = this.getRepository().obtenerPagosReserva(reservaId);
        for (Pago pago : pagos) {
            pago.setReserva(reservaRepository.obtenerPorId(pago.getReserva().getId()));
            pago.setCaja(cajaService.obtenerPorId(pago.getCaja().getId()));
            pago.setUsuario(usuarioService.obtenerPorId(pago.getUsuario().getId()));

        }
        return pagos;
    }

}