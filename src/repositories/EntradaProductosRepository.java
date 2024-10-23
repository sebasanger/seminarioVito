package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.EntradaProducto;

public class EntradaProductosRepository extends AbstractGenericRepository<EntradaProducto, Integer> {

    @Override
    protected String getTabla() {
        return "entradasProductos";
    }

    @Override
    protected EntradaProducto mapeoEntidad(ResultSet rs) throws SQLException {
        return new EntradaProducto(rs.getInt("id"), rs.getDouble("precioUnitario"), rs.getInt("cantidad"),
                rs.getDate("fecha"), rs.getInt("productos_id"), rs.getInt("usuarios_id"));
    }

    @Override
    public void crear(EntradaProducto entradaProducto) throws SQLException {
        String sql = "INSERT INTO entradasProductos (precioUnitario, cantidad, fecha, productos_id, usuarios_id) VALUES (?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, entradaProducto.getPrecioUnitario());
            stmt.setInt(2, entradaProducto.getCantidad());
            stmt.setDate(3, new java.sql.Date(entradaProducto.getFecha().getTime()));
            stmt.setInt(4, entradaProducto.getProducto().getId());
            stmt.setInt(5, entradaProducto.getUsuario().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(EntradaProducto entradaProducto) throws SQLException {
        String sql = "UPDATE entradasProductos SET precioUnitario = ?, cantidad = ?, fecha = ?, productos_id = ?, usuarios_id = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, entradaProducto.getPrecioUnitario());
            stmt.setInt(2, entradaProducto.getCantidad());
            stmt.setDate(3, new java.sql.Date(entradaProducto.getFecha().getTime()));
            stmt.setInt(4, entradaProducto.getProducto().getId());
            stmt.setInt(5, entradaProducto.getUsuario().getId());
            stmt.setInt(6, entradaProducto.getId());
            stmt.executeUpdate();
        }
    }

}
