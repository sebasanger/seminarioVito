package controllers;

import java.sql.SQLException;
import java.util.List;

import exceptions.PagoFaltanteException;
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

    public void generarCheckIn(Integer reservaId) throws SQLException {
        this.reservaService.generarCheckInReserva(reservaId);
    }

    public void generarCheckOut(Integer reservaId) throws SQLException, PagoFaltanteException {
        this.reservaService.generarCheckOutReserva(reservaId);
    }

    public List<Reserva> obtenerReservasPorEstadoYFecha(String status, Boolean porFechaFin)
            throws SQLException {
        return this.reservaService.obtenerReservasPorEstadoYFecha(status, porFechaFin);
    }

}
