package services;

import models.PrecioHabitacion;
import repositories.PrecioHabitacionRepository;

public class PrecioHabitacionService extends AbstractGenericService<PrecioHabitacion, Integer> {
    private PrecioHabitacionRepository precioHabitacionRepository = new PrecioHabitacionRepository();

    @Override
    protected PrecioHabitacionRepository getRepository() {
        return precioHabitacionRepository;
    }

}