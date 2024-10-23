package controllers;

import models.PrecioHabitacion;
import services.PrecioHabitacionService;

public class PrecioHabitacionController extends AbstractGenericController<PrecioHabitacion, Integer> {

    private PrecioHabitacionService precioHabitacionService = new PrecioHabitacionService();

    @Override
    protected PrecioHabitacionService getService() {
        return precioHabitacionService;
    }

}
