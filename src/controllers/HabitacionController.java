package controllers;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import models.Habitacion;
import services.HabitacionService;

public class HabitacionController extends AbstractGenericController<Habitacion, Integer> {

    private HabitacionService habitacionService = new HabitacionService();

    @Override
    protected HabitacionService getService() {
        return habitacionService;
    }

    public List<Habitacion> obtenerHabitacionesLibres(Date fechaInicio, Date fechaFin,
            Integer capacidad) throws SQLException {
        return this.habitacionService.obtenerHabitacionesLibres(fechaInicio, fechaFin, capacidad);
    }

}
