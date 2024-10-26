package controllers;

import java.sql.SQLException;

import exceptions.StockInsuficienteException;
import models.Consumicion;
import services.ConsumoService;

public class ConsumoController extends AbstractGenericController<Consumicion, Integer> {

    private ConsumoService consumoService = new ConsumoService();

    @Override
    protected ConsumoService getService() {
        return consumoService;
    }

    public void crearConsumo(Consumicion consumicion) throws SQLException, StockInsuficienteException {
        consumoService.crearConsumo(consumicion);
    }

}
