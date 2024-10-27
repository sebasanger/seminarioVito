package controllers;

import java.sql.SQLException;
import java.util.List;

import exceptions.CajaNoAbiertaException;
import exceptions.PagoExcedidoException;
import models.Pago;
import services.PagoService;

public class PagoController extends AbstractGenericController<Pago, Integer> {

    private PagoService pagoService = new PagoService();

    @Override
    protected PagoService getService() {
        return pagoService;
    }

    // no se sobrescribe el metodo de ya que cambia la firma,
    // llamando a otro metodo en el servicio y retornando mas excepciones
    public void crearPago(Pago pago) throws SQLException, PagoExcedidoException, CajaNoAbiertaException {
        pagoService.crearPago(pago);

    }

    // se genera un aviso de que el metodo no esta implementado
    // con la misma firma del metodo del generico
    @Override
    public void crear(Pago pago) throws SQLException {
        throw new SQLException("Metodo no implementado");
    }

    // busca todos los pagos de una reserva
    public List<Pago> obtenerPagosReserva(Integer reservaId) throws SQLException {
        return getService().obtenerPagosReserva(reservaId);
    }

}
