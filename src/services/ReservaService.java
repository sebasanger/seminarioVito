package services;

import java.sql.SQLException;
import java.util.List;

import models.EstadoReservaEnum;
import models.Reserva;
import repositories.ClienteRepository;
import repositories.HabitacionRepository;
import repositories.PrecioHabitacionRepository;
import repositories.ReservaRepository;
import repositories.UsuarioRepository;
import utils.DateUtils;

public class ReservaService extends AbstractGenericService<Reserva, Integer> {
    private ReservaRepository reservaRepository = new ReservaRepository();
    private PrecioHabitacionRepository precioHabitacionRepository = new PrecioHabitacionRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository();
    private HabitacionRepository habitacionRepository = new HabitacionRepository();
    private ClienteRepository clienteRepository = new ClienteRepository();

    @Override
    protected ReservaRepository getRepository() {
        return reservaRepository;
    }

    @Override
    public void crear(Reserva reserva) throws SQLException {

        reserva.setPrecioDiario(reserva.getPrecioHabitacion().getPrecio());

        Double precioTotal = getPrecioTotal(reserva.getFechaInicio(), reserva.getFechaFin(), reserva.getPrecioDiario());
        reserva.setPrecioTotal(precioTotal);
        reserva.setPagadoTotal((double) 0);

        if (DateUtils.mismaFecha(reserva.getFechaInicio(), reserva.getFechaCreacion())) {
            reserva.setEstado(EstadoReservaEnum.ACTIVA.getEstado());
        } else {
            reserva.setEstado(EstadoReservaEnum.PENDIENTE.getEstado());
        }

        System.out.println("Reserva creada con éxito.");
        System.out.println(reserva);

        reservaRepository.crear(reserva);
    }

    @Override
    public List<Reserva> obtenerTodos() throws SQLException {

        List<Reserva> reservas = reservaRepository.obtenerTodos();

        for (Reserva reserva : reservas) {
            reserva = this.obtenerDatosRelacionadosReserva(reserva);
        }

        return reservas;
    }

    @Override
    public Reserva obtenerPorId(Integer id) throws SQLException {
        Reserva reserva = reservaRepository.obtenerPorId(id);

        return this.obtenerDatosRelacionadosReserva(reserva);
    }

    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        return this.reservaRepository.obtenerReservasPorEstado(status);
    }

    private Double getPrecioTotal(java.util.Date fechaInicio, java.util.Date fechaFin, Double precioDiario) {
        Long cantidadDias = DateUtils.getDiffInDays(fechaInicio, fechaFin);
        return cantidadDias * precioDiario;
    }

    private Reserva obtenerDatosRelacionadosReserva(Reserva reserva) throws SQLException {
        reserva.setPrecioHabitacion(precioHabitacionRepository.obtenerPorId(reserva.getPrecioHabitacion().getId()));
        reserva.setHabitacion(habitacionRepository.obtenerPorId(reserva.getHabitacion().getId()));
        reserva.setUsuario(usuarioRepository.obtenerPorId(reserva.getUsuario().getId()));
        reserva.setClientes(clienteRepository.obtenerClientesReserva(reserva.getId()));
        return reserva;
    }

}