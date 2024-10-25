package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;
import models.Cliente;
import models.Habitacion;
import models.PrecioHabitacion;
import models.Reserva;
import models.Usuario;
import utils.DateUtils;

public class ReservaRepository extends AbstractGenericRepository<Reserva, Integer> {

    @Override
    protected String getTabla() {
        return "reservas";
    }

    @Override
    protected Reserva mapeoEntidad(ResultSet rs) throws SQLException {

        return new Reserva(rs.getInt("id"), rs.getDate("checkIn"), rs.getDate("checkOut"), rs.getDate("fechaCreacion"),
                rs.getDate("fechaInicio"), rs.getDate("fechaFin"), rs.getString("origen"),
                rs.getString("destino"), rs.getDouble("precioDiario"), rs.getDouble("precioTotal"),
                rs.getDouble("pagadoTotal"), rs.getString("estado"),
                new Habitacion(rs.getInt("habitaciones_id")),
                new PrecioHabitacion(rs.getInt("precios_habitaciones_id")),
                new Usuario(rs.getInt("usuarios_id")));
    }

    @Override
    public void crear(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (checkIn, checkOut, fechaCreacion, fechaInicio, fechaFin, origen, destino, precioDiario, precioTotal, pagadoTotal, estado, habitaciones_id, precios_habitaciones_id, usuarios_id ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            stmt.setDate(1, DateUtils.transformDateUtilToSql(reserva.getCheckIn()));
            stmt.setDate(2, DateUtils.transformDateUtilToSql(reserva.getCheckOut()));
            stmt.setDate(3, DateUtils.transformDateUtilToSql(reserva.getFechaCreacion()));
            stmt.setDate(4, DateUtils.transformDateUtilToSql(reserva.getFechaInicio()));
            stmt.setDate(5, DateUtils.transformDateUtilToSql(reserva.getFechaFin()));

            stmt.setString(6, reserva.getOrigen());
            stmt.setString(7, reserva.getDestino());

            stmt.setDouble(8, reserva.getPrecioDiario() != null ? reserva.getPrecioDiario() : 0);
            stmt.setDouble(9, reserva.getPagadoTotal() != null ? reserva.getPagadoTotal() : 0);
            stmt.setDouble(10, reserva.getPrecioTotal() != null ? reserva.getPrecioTotal() : 0);

            stmt.setString(11, reserva.getEstado());

            stmt.setInt(12, reserva.getHabitacion().getId());
            stmt.setInt(13, reserva.getPrecioHabitacion().getId());
            stmt.setInt(14, reserva.getUsuario().getId());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int reservaId = generatedKeys.getInt(1);

                insertarClientesReserva(conn, reserva.getClientes(), reservaId);
            } else {
                throw new SQLException("Error al obtener el ID de la reserva insertada.");
            }
        }
    }

    private void insertarClientesReserva(Connection conn, List<Cliente> clientes, int reservaId) throws SQLException {
        String sqlClientesReservas = "INSERT INTO reservas_clientes (clientes_id, reservas_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sqlClientesReservas)) {
            for (Cliente cliente : clientes) {
                stmt.setInt(1, cliente.getId());
                stmt.setInt(2, reservaId);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void actualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE reservas SET checkIn = ?, checkOut = ?, fechaCreacion = ?, fechaInicio = ?, fechaFin = ?, origen = ? , destino = ?, precioDiario = ?, precioTotal = ?, pagadoTotal = ?, estado = ?, habitaciones_id = ?, precios_habitaciones_id = ? , usuarios_id = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, DateUtils.transformDateUtilToSql(reserva.getCheckIn()));
            stmt.setDate(2, DateUtils.transformDateUtilToSql(reserva.getCheckOut()));
            stmt.setDate(3, DateUtils.transformDateUtilToSql(reserva.getFechaCreacion()));
            stmt.setDate(4, DateUtils.transformDateUtilToSql(reserva.getFechaInicio()));
            stmt.setDate(5, DateUtils.transformDateUtilToSql(reserva.getFechaFin()));

            stmt.setString(6, reserva.getOrigen());
            stmt.setString(7, reserva.getDestino());

            stmt.setDouble(8, reserva.getPrecioDiario() != null ? reserva.getPrecioDiario() : 0);
            stmt.setDouble(9, reserva.getPrecioTotal() != null ? reserva.getPrecioTotal() : 0);
            stmt.setDouble(10, reserva.getPagadoTotal() != null ? reserva.getPagadoTotal() : 0);

            stmt.setString(11, reserva.getEstado());

            stmt.setInt(12, reserva.getHabitacion().getId());
            stmt.setInt(13, reserva.getPrecioHabitacion().getId());
            stmt.setInt(14, reserva.getUsuario().getId());
            stmt.setInt(15, reserva.getId());
            stmt.executeUpdate();
        }
    }

    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        String sql = "SELECT * FROM " + getTabla() + " WHERE estado = ?";
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservas.add(mapeoEntidad(rs));
                }
            }

        }
        return reservas;
    }

}
