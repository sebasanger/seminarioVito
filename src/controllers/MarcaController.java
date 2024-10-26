package controllers;

import models.Marca;
import services.MarcaService;

public class MarcaController extends AbstractGenericController<Marca, Integer> {

    private MarcaService marcaService = new MarcaService();

    @Override
    protected MarcaService getService() {
        return marcaService;
    }

}
