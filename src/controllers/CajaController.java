package controllers;

import java.sql.SQLException;

import exceptions.CajaNoAbiertaException;
import models.Caja;
import services.CajaService;

public class CajaController extends AbstractGenericController<Caja, Integer> {

    private CajaService cajaService = new CajaService();

    @Override
    protected CajaService getService() {
        return cajaService;
    }

    // busca la caja activa actualmente
    public Caja obtenerCajaActiva() throws SQLException, CajaNoAbiertaException {
        return this.cajaService.obtenerCajaActiva();
    }

}
