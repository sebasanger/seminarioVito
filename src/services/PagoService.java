package services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import exceptions.CajaNoAbiertaException;
import exceptions.PagoExcedidoException;
import models.Caja;
import models.Pago;
import models.Reserva;
import repositories.CajaRepository;
import repositories.PagoRepository;
import repositories.ReservaRepository;
import repositories.UsuarioRepository;

public class PagoService extends AbstractGenericService<Pago, Integer> {
    private PagoRepository pagoRepository = new PagoRepository();
    private ReservaService reservaService = new ReservaService();
    private CajaService cajaService = new CajaService();

    @Override
    protected PagoRepository getRepository() {
        return pagoRepository;
    }

    @Override
    public List<Pago> obtenerTodos() throws SQLException {
        ReservaRepository reservaRepository = new ReservaRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        CajaRepository cajaRepository = new CajaRepository();

        List<Pago> pagos = this.getRepository().obtenerTodos();

        for (Pago pago : pagos) {
            pago.setReserva(reservaRepository.obtenerPorId(pago.getReserva().getId()));
            pago.setCaja(cajaRepository.obtenerPorId(pago.getCaja().getId()));
            pago.setUsuario(usuarioRepository.obtenerPorId(pago.getUsuario().getId()));

        }

        return pagos;
    }

    public void crearPago(Pago pago) throws SQLException, PagoExcedidoException, CajaNoAbiertaException {
        Date fechaActual = new Date(new java.util.Date().getTime());
        pago.setFecha(fechaActual);

        Caja caja = cajaService.obtenerCajaActiva();
        pago.setCaja(caja);

        Double faltantePorPagar = pago.getReserva().getPrecioTotal() - pago.getReserva().getPagadoTotal();

        if (pago.getCantidad() > faltantePorPagar) {
            throw new PagoExcedidoException();
        }

        pagoRepository.crear(pago);
        Reserva reservaPagada = reservaService.obtenerPorId(pago.getReserva().getId());

        Double nuevoTotalPagado = reservaPagada.getPagadoTotal() + pago.getCantidad();

        reservaPagada.setPagadoTotal(nuevoTotalPagado);

        reservaService.actualizar(reservaPagada);

    }

}