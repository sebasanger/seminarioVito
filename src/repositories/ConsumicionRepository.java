package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;
import models.Consumicion;

public class ConsumicionRepository extends AbstractGenericRepository<Consumicion, Integer> {

    @Override
    protected String getTabla() {
        return "consumiciones";
    }

    @Override
    public List<Consumicion> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM " + getTabla();
        List<Consumicion> entidades = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                entidades.add(mapeoEntidad(rs));
            }
        }
        return entidades;
    }

    @Override
    protected Consumicion mapeoEntidad(ResultSet rs) throws SQLException {
        return new Consumicion(rs.getInt("id"), rs.getInt("cantidad"), rs.getDouble("precioTotal"),
                rs.getDouble("precioUnitario"), rs.getDate("fecha"), rs.getInt("reservas_id"),
                rs.getInt("productos_id"), rs.getInt("usuarios_id"));
    }

    @Override
    public void crear(Consumicion consumicion) throws SQLException {
        String sql = "INSERT INTO consumiciones (cantidad, precioTotal, precioUnitario, fecha, reservas_id, productos_id, usuarios_id) VALUES (?,?,?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consumicion.getCantidad());
            stmt.setDouble(2, consumicion.getPrecioTotal());
            stmt.setDouble(3, consumicion.getPrecioUnitario());
            stmt.setDate(4, new java.sql.Date(consumicion.getFecha().getTime()));
            stmt.setInt(5, consumicion.getReserva().getId());
            stmt.setInt(6, consumicion.getProducto().getId());
            stmt.setInt(7, consumicion.getUsuario().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Consumicion consumicion) throws SQLException {
        String sql = "UPDATE consumiciones SET cantidad = ?, precioTotal = ?, precioUnitario = ?, fecha = ?, reservas_id = ?, productos_id = ? , usuarios_id = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, consumicion.getCantidad());
            stmt.setDouble(2, consumicion.getPrecioTotal());
            stmt.setDouble(3, consumicion.getPrecioUnitario());
            stmt.setDate(4, new java.sql.Date(consumicion.getFecha().getTime()));
            stmt.setInt(5, consumicion.getReserva().getId());
            stmt.setInt(6, consumicion.getProducto().getId());
            stmt.setInt(7, consumicion.getUsuario().getId());
            stmt.setInt(8, consumicion.getId());
            stmt.executeUpdate();
        }
    }

}
