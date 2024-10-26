package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.Producto;

public class ProductoRepository extends AbstractGenericRepository<Producto, Integer> {

    @Override
    protected String getTabla() {
        return "productos";
    }

    @Override
    protected Producto mapeoEntidad(ResultSet rs) throws SQLException {
        return new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getString("descripcion"),
                rs.getDouble("precio"), rs.getInt("stock"), rs.getInt("marcas_id"), rs.getInt("categorias_id"));
    }

    @Override
    public void crear(Producto precioHabitacion) throws SQLException {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, marcas_id, categorias_id) VALUES (?,?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, precioHabitacion.getNombre());
            stmt.setString(2, precioHabitacion.getDescripcion());
            stmt.setDouble(3, precioHabitacion.getPrecio());
            stmt.setInt(4, precioHabitacion.getStock());
            stmt.setInt(5, precioHabitacion.getMarca().getId());
            stmt.setInt(6, precioHabitacion.getCategoria().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Producto producto) throws SQLException {
        actualizar(producto, null);
    }

    public void actualizar(Producto producto, Connection conn) throws SQLException {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ?, marcas_id = ?, categorias_id = ? WHERE id = ?";
        boolean cerrarConexion = false; // Variable para manejar el cierre opcional de la conexión

        // Si no se proporciona una conexión, crea una nueva y marca para cierre
        if (conn == null) {
            conn = MySQLConnection.getConnection();
            cerrarConexion = true;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setInt(5, producto.getMarca().getId());
            stmt.setInt(6, producto.getCategoria().getId());
            stmt.setInt(7, producto.getId());
            stmt.executeUpdate();
        } finally {
            // Cierra la conexión solo si fue creada internamente en este método
            if (cerrarConexion && conn != null) {
                conn.close();
            }
        }
    }

}
