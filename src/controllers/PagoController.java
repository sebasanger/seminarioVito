package controllers;

import java.sql.SQLException;

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

    public void crearPago(Pago pago) throws SQLException, PagoExcedidoException, CajaNoAbiertaException {
        pagoService.crearPago(pago);

    }

}
