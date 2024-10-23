package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;
import models.Reserva;

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
                rs.getInt("habitaciones_id"), rs.getInt("precios_habitaciones_id"), rs.getInt("usuarios_id"));
    }

    @Override
    public void crear(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (checkIn, checkOut, fechaCreacion, fechaInicio, fechaFin, origen, destino, precioDiario, precioTotal, pagadoTotal, estado, habitaciones_id, preciosHabitaciones_id, usuarios_id ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(reserva.getCheckIn().getTime()));
            stmt.setDate(2, new java.sql.Date(reserva.getCheckOut().getTime()));
            stmt.setDate(3, new java.sql.Date(reserva.getFechaCreacion().getTime()));
            stmt.setDate(4, new java.sql.Date(reserva.getFechaInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(reserva.getFechaFin().getTime()));

            stmt.setString(6, reserva.getOrigen());
            stmt.setString(7, reserva.getDestino());

            stmt.setDouble(8, reserva.getPrecioDiario());
            stmt.setDouble(9, reserva.getPagadoTotal());
            stmt.setDouble(10, reserva.getPagadoTotal());

            stmt.setString(11, reserva.getEstado());

            stmt.setInt(12, reserva.getHabitacion().getId());
            stmt.setInt(13, reserva.getPrecioHabitacion().getId());
            stmt.setInt(14, reserva.getUsuario().getId());

            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE consumiciones SET checkIn = ?, checkOut = ?, fechaCreacion = ?, fechaInicio = ?, fechaFin = ?, origen = ? , destino = ?, precioDiario = ?, precioTotal = ?, pagadoTotal = ?, estado = ?, habitaciones_id = ?, preciosHabitaciones_id = ? , usuarios_id = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, new java.sql.Date(reserva.getCheckIn().getTime()));
            stmt.setDate(2, new java.sql.Date(reserva.getCheckOut().getTime()));
            stmt.setDate(3, new java.sql.Date(reserva.getFechaCreacion().getTime()));
            stmt.setDate(4, new java.sql.Date(reserva.getFechaInicio().getTime()));
            stmt.setDate(5, new java.sql.Date(reserva.getFechaFin().getTime()));

            stmt.setString(6, reserva.getOrigen());
            stmt.setString(7, reserva.getDestino());

            stmt.setDouble(8, reserva.getPrecioDiario());
            stmt.setDouble(9, reserva.getPagadoTotal());
            stmt.setDouble(10, reserva.getPagadoTotal());

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
