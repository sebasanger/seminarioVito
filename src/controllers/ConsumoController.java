package controllers;

import models.Consumicion;
import services.ConsumoService;

public class ConsumoController extends AbstractGenericController<Consumicion, Integer> {

    private ConsumoService consumoService = new ConsumoService();

    @Override
    protected ConsumoService getService() {
        return consumoService;
    }

}
