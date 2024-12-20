package services;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import models.Habitacion;
import repositories.HabitacionRepository;

public class HabitacionService extends AbstractGenericService<Habitacion, Integer> {
    private HabitacionRepository habitacionRepository = new HabitacionRepository();

    @Override
    protected HabitacionRepository getRepository() {
        return habitacionRepository;
    }

    // busca todas las habitaciones libres entre dos fechas y con una capacidad
    // minima
    public List<Habitacion> obtenerHabitacionesLibres(Date fechaInicio, Date fechaFin,
            Integer capacidad) throws SQLException {
        return this.habitacionRepository.obtenerHabitacionesLibres(fechaInicio, fechaFin, capacidad);
    }

}