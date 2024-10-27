package services;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import exceptions.PagoFaltanteException;
import models.EstadoReservaEnum;
import models.Reserva;
import repositories.ConsumicionRepository;
import repositories.ReservaRepository;
import utils.DateUtils;

public class ReservaService extends AbstractGenericService<Reserva, Integer> {
    private ReservaRepository reservaRepository = new ReservaRepository();
    private ConsumicionRepository consumicionRepository = new ConsumicionRepository();

    @Override
    protected ReservaRepository getRepository() {
        return reservaRepository;
    }

    @Override
    public void crear(Reserva reserva) throws SQLException {

        reserva.setPrecioDiario(reserva.getPrecioHabitacion().getPrecio());

        Double precioTotal = getPrecioTotal(reserva.getFechaInicio(), reserva.getFechaFin(), reserva.getPrecioDiario(),
                null);
        reserva.setPrecioTotal(precioTotal);
        reserva.setPagadoTotal((double) 0);

        if (DateUtils.mismaFecha(reserva.getFechaInicio(), reserva.getFechaCreacion())) {
            reserva.setCheckIn(reserva.getFechaCreacion());
            reserva.setEstado(EstadoReservaEnum.ACTIVA.getEstado());
        } else {
            reserva.setEstado(EstadoReservaEnum.PENDIENTE.getEstado());
        }

        reservaRepository.crear(reserva);
    }

    @Override
    public void actualizar(Reserva reserva) throws SQLException {

        // evaluamos el precio nuevamente por el precio diario mas las consumciones
        // realizadas por la reserva
        reserva.setPrecioDiario(reserva.getPrecioHabitacion().getPrecio());
        Double precioTotal = getPrecioTotal(reserva.getFechaInicio(), reserva.getFechaFin(), reserva.getPrecioDiario(),
                reserva.getId());
        reserva.setPrecioTotal(precioTotal);

        // actualizamos si se cambia para el dia de la fecha la reserva cambiamos el
        // estado de la reserva a activa sino quedaria a pendiente
        LocalDate fechaActual = LocalDate.now();
        if (DateUtils.mismaFecha(reserva.getFechaInicio(), Date.valueOf(fechaActual))) {
            reserva.setCheckIn(Date.valueOf(fechaActual));
            reserva.setEstado(EstadoReservaEnum.ACTIVA.getEstado());
        } else {
            reserva.setEstado(EstadoReservaEnum.PENDIENTE.getEstado());
        }

        reservaRepository.actualizar(reserva);
    }

    @Override
    public List<Reserva> obtenerTodos() throws SQLException {
        return this.reservaRepository.obtenerTodos();
    }

    @Override
    public Reserva obtenerPorId(Integer id) throws SQLException {
        return reservaRepository.obtenerPorId(id);
    }

    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        return this.reservaRepository.obtenerReservasPorEstado(status);
    }

    public List<Reserva> obtenerReservasPorEstadoYFecha(String status, Boolean porFechaFin)
            throws SQLException {

        LocalDate fechaActual = LocalDate.now();

        return this.reservaRepository.obtenerReservasPorEstadoYFecha(status, Date.valueOf(fechaActual), porFechaFin);
    }

    private Double getPrecioTotal(java.util.Date fechaInicio, java.util.Date fechaFin, Double precioDiario,
            Integer reservaId) throws SQLException {
        Double totalConsumiciones = 0D;
        if (reservaId != null) {
            totalConsumiciones = consumicionRepository.obtenerTotalConsumcionesPorReserva(reservaId);
        }
        Long cantidadDias = DateUtils.getDiffInDays(fechaInicio, fechaFin);
        return (cantidadDias * precioDiario) + totalConsumiciones;
    }

    public void generarCheckInReserva(Integer reservaId) throws SQLException {
        reservaRepository.cambiarEstadoReserva(reservaId, EstadoReservaEnum.ACTIVA);
    }

    public void generarCheckOutReserva(Integer reservaId) throws SQLException, PagoFaltanteException {
        Reserva reserva = reservaRepository.obtenerPorId(reservaId);
        if (reserva.getPagadoTotal() < reserva.getPrecioTotal()) {
            System.out.println("Faltante: " + (reserva.getPrecioTotal() - reserva.getPagadoTotal()));
            throw new PagoFaltanteException();
        }
        reservaRepository.cambiarEstadoReserva(reservaId, EstadoReservaEnum.FINALIZADA);
    }

}