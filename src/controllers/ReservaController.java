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

    // obtiene las reservas por un estado
    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        return this.reservaService.obtenerReservasPorEstado(status);
    }

    // genera el check in
    public void generarCheckIn(Integer reservaId) throws SQLException {
        this.reservaService.generarCheckInReserva(reservaId);
    }

    // genera el check out
    public void generarCheckOut(Integer reservaId) throws SQLException, PagoFaltanteException {
        this.reservaService.generarCheckOutReserva(reservaId);
    }

    // busca las reservas por un estado y una fecha
    // boolean por si lo que se busca es la fecha de inicio o fecha de fin
    public List<Reserva> obtenerReservasPorEstadoYFecha(String status, Boolean porFechaFin)
            throws SQLException {
        return this.reservaService.obtenerReservasPorEstadoYFecha(status, porFechaFin);
    }

}
