package controllers;

import java.sql.SQLException;
import java.util.List;

import models.Reserva;
import services.ReservaService;

public class ReservaController extends AbstractGenericController<Reserva, Integer> {

    private ReservaService reservaService = new ReservaService();

    @Override
    protected ReservaService getService() {
        return reservaService;
    }

    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        return this.reservaService.obtenerReservasPorEstado(status);
    }

}
