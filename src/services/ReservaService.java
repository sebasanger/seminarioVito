package services;

import java.sql.SQLException;
import java.util.List;

import models.Reserva;
import repositories.ClienteRepository;
import repositories.HabitacionRepository;
import repositories.PrecioHabitacionRepository;
import repositories.ReservaRepository;
import repositories.UsuarioRepository;

public class ReservaService extends AbstractGenericService<Reserva, Integer> {
    private ReservaRepository reservaRepository = new ReservaRepository();

    @Override
    protected ReservaRepository getRepository() {
        return reservaRepository;
    }

    @Override
    public List<Reserva> obtenerTodos() throws SQLException {

        PrecioHabitacionRepository precioHabitacionRepository = new PrecioHabitacionRepository();
        UsuarioRepository usuarioRepository = new UsuarioRepository();
        HabitacionRepository habitacionRepository = new HabitacionRepository();
        ClienteRepository clienteRepository = new ClienteRepository();

        List<Reserva> reservas = reservaRepository.obtenerTodos();

        for (Reserva reserva : reservas) {
            reserva.setPrecioHabitacion(precioHabitacionRepository.obtenerPorId(reserva.getPrecioHabitacionId()));
            reserva.setHabitacion(habitacionRepository.obtenerPorId(reserva.getPrecioHabitacionId()));
            reserva.setUsuario(usuarioRepository.obtenerPorId(reserva.getUsuarioId()));
            reserva.setClientes(clienteRepository.obtenerClientesReserva(reserva.getId()));

        }

        return reservas;
    }

    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        return this.reservaRepository.obtenerReservasPorEstado(status);
    }

}