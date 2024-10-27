package services;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import exceptions.PagoFaltanteException;
import models.EstadoReservaEnum;
import models.Reserva;
import repositories.ClienteRepository;
import repositories.ConsumicionRepository;
import repositories.ReservaRepository;
import utils.DateUtils;

//clase servicio principal para trabajar las reservas
public class ReservaService extends AbstractGenericService<Reserva, Integer> {
    private ReservaRepository reservaRepository = new ReservaRepository();
    private ConsumicionRepository consumicionRepository = new ConsumicionRepository();
    private ClienteRepository clienteRepository = new ClienteRepository();

    // obtiene el repositorio para trabajar con la entidad
    @Override
    protected ReservaRepository getRepository() {
        return reservaRepository;
    }

    @Override
    public void crear(Reserva reserva) throws SQLException {

        // guarda el precio diario con el precio de la habitacion
        // por si se actualiza el precio de la habitacion a futuro pero se tiene
        // guardado el precio que tenia la habitacion en ese momento
        reserva.setPrecioDiario(reserva.getPrecioHabitacion().getPrecio());

        // se calcula el precio total de la reserva con el precio diario multiplicado
        // por los dias de estadia
        Double precioTotal = getPrecioTotal(reserva.getFechaInicio(), reserva.getFechaFin(), reserva.getPrecioDiario(),
                null);
        reserva.setPrecioTotal(precioTotal);
        reserva.setPagadoTotal((double) 0);

        // si la fecha de inicio de la reserva es igual que la fecha de creacion se
        // genera inmediatamente el check in
        // tambien se cambia el estado de la reserva a ACTIVA
        if (DateUtils.mismaFecha(reserva.getFechaInicio(), reserva.getFechaCreacion())) {
            reserva.setCheckIn(reserva.getFechaCreacion());
            reserva.setEstado(EstadoReservaEnum.ACTIVA.getEstado());
        } else {
            // sino se guarda como reserva pendiente
            reserva.setEstado(EstadoReservaEnum.PENDIENTE.getEstado());
        }

        reservaRepository.crear(reserva);
    }

    @Override
    public void actualizar(Reserva reserva) throws SQLException {

        // evaluamos el precio nuevamente por el precio diario mas las consumciones
        // realizadas por la reserva y el precio diario por la cantidad de dias
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
    // busca todas las reservas
    public List<Reserva> obtenerTodos() throws SQLException {
        List<Reserva> reservas = this.reservaRepository.obtenerTodos();
        for (Reserva reserva : reservas) {
            reserva.setClientes(clienteRepository.obtenerClientesReserva(reserva.getId()));
        }
        return reservas;
    }

    @Override
    // busca la reserva por un id
    public Reserva obtenerPorId(Integer id) throws SQLException {
        Reserva reserva = reservaRepository.obtenerPorId(id);
        reserva.setClientes(clienteRepository.obtenerClientesReserva(id));
        return reserva;
    }

    // busca todas las reservas por un estado
    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        return this.reservaRepository.obtenerReservasPorEstado(status);
    }

    // busca todas las reservas por un estado y una fecha
    public List<Reserva> obtenerReservasPorEstadoYFecha(String status, Boolean porFechaFin)
            throws SQLException {
        LocalDate fechaActual = LocalDate.now();
        return this.reservaRepository.obtenerReservasPorEstadoYFecha(status, Date.valueOf(fechaActual), porFechaFin);
    }

    // obtiene de la reserva el precio total calculando el precio por dia por la
    // cantidad de dias y busca las consumiciones realizadas si es que las tiene en
    // la reserva para sumarlo al monto
    private Double getPrecioTotal(java.util.Date fechaInicio, java.util.Date fechaFin, Double precioDiario,
            Integer reservaId) throws SQLException {
        Double totalConsumiciones = 0D;
        if (reservaId != null) {
            totalConsumiciones = consumicionRepository.obtenerTotalConsumcionesPorReserva(reservaId);
        }
        Long cantidadDias = DateUtils.getDiffInDays(fechaInicio, fechaFin);
        return (cantidadDias * precioDiario) + totalConsumiciones;
    }

    // genera el check in de la reserva y cambia su estado
    public void generarCheckInReserva(Integer reservaId) throws SQLException {
        reservaRepository.cambiarEstadoReserva(reservaId, EstadoReservaEnum.ACTIVA);
    }

    // genera el check out de la reserva y cambia su estado
    public void generarCheckOutReserva(Integer reservaId) throws SQLException, PagoFaltanteException {
        Reserva reserva = reservaRepository.obtenerPorId(reservaId);
        // evalua que en la reserva se haya realizado todos los pagos correspondientes
        // sino da una excepcion diciendo que faltan pagos
        if (reserva.getPagadoTotal() < reserva.getPrecioTotal()) {
            System.out.println("Faltante: " + (reserva.getPrecioTotal() - reserva.getPagadoTotal()));
            throw new PagoFaltanteException();
        }
        reservaRepository.cambiarEstadoReserva(reservaId, EstadoReservaEnum.FINALIZADA);
    }

}