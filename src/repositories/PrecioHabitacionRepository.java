package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.PrecioHabitacion;

public class PrecioHabitacionRepository extends AbstractGenericRepository<PrecioHabitacion, Integer> {

    @Override
    protected String getTabla() {
        return "precios_habitaciones";
    }

    @Override
    protected PrecioHabitacion mapeoEntidad(ResultSet rs) throws SQLException {
        return new PrecioHabitacion(rs.getInt("id"), rs.getDouble("precio"), rs.getString("descripcion"),
                rs.getBoolean("disponible"), rs.getDate("fechaCreacion"));
    }

    protected PrecioHabitacion mapeoEntidad(ResultSet rs, String idName) throws SQLException {
        String id = "id";
        if (idName == null) {
            id = idName;
        }
        return new PrecioHabitacion(rs.getInt(id), rs.getDouble("precio"), rs.getString("descripcion"),
                rs.getBoolean("disponible"), rs.getDate("fechaCreacion"));
    }

    @Override
    public void crear(PrecioHabitacion precioHabitacion) throws SQLException {
        String sql = "INSERT INTO precios_habitaciones (precio, descripcion, disponible, fechaCreacion) VALUES (?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, precioHabitacion.getPrecio());
            stmt.setString(2, precioHabitacion.getDescripcion());
            stmt.setBoolean(3, precioHabitacion.getDisponible());
            stmt.setDate(4, new java.sql.Date(precioHabitacion.getFechaCreacion().getTime()));
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(PrecioHabitacion precioHabitacion) throws SQLException {
        String sql = "UPDATE precios_habitaciones SET precio = ?, descripcion = ?, disponible = ?, fechaCreacion = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, precioHabitacion.getPrecio());
            stmt.setString(2, precioHabitacion.getDescripcion());
            stmt.setBoolean(3, precioHabitacion.getDisponible());
            stmt.setDate(4, new java.sql.Date(precioHabitacion.getFechaCreacion().getTime()));
            stmt.setInt(5, precioHabitacion.getId());
            stmt.executeUpdate();
        }
    }

}
