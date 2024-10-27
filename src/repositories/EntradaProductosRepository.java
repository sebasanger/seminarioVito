package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.EntradaProducto;
import models.Producto;

public class EntradaProductosRepository extends AbstractGenericRepository<EntradaProducto, Integer> {

    private ProductoRepository productoRepository = new ProductoRepository();

    @Override
    protected String getTabla() {
        return "entradas_productos";
    }

    @Override
    protected EntradaProducto mapeoEntidad(ResultSet rs) throws SQLException {
        return new EntradaProducto(rs.getInt("id"), rs.getDouble("precioUnitario"), rs.getInt("cantidad"),
                rs.getDate("fecha"), rs.getInt("productos_id"), rs.getInt("usuarios_id"));
    }

    @Override
    public void crear(EntradaProducto entradaProducto) throws SQLException {
        String sql = "INSERT INTO entradas_productos (precioUnitario, cantidad, fecha, productos_id, usuarios_id) VALUES (?,?,?,?,?)";
        Connection conn = null;
        try {

            // Comienza la transacción
            // desahilita que se ejecute automaticamente la transaccion
            conn = MySQLConnection.getConnection();
            conn.setAutoCommit(false);

            // Actualizar stock del producto
            Producto producto = entradaProducto.getProducto();
            producto.setStock(producto.getStock() + entradaProducto.getCantidad());
            productoRepository.actualizar(producto, conn);

            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDouble(1, entradaProducto.getPrecioUnitario());
            stmt.setInt(2, entradaProducto.getCantidad());
            stmt.setDate(3, new java.sql.Date(entradaProducto.getFecha().getTime()));
            stmt.setInt(4, entradaProducto.getProducto().getId());
            stmt.setInt(5, entradaProducto.getUsuario().getId());
            stmt.executeUpdate();

            // se ejecuta si todo sale bien
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir transacción en caso de error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;

        }
    }

    @Override
    public void eliminar(Integer id) throws SQLException {
        String sql = "DELETE FROM " + getTabla() + " WHERE id = ?";
        Connection conn = null;

        // busca los datos de la entrada de producto y el prducto en cuestion
        EntradaProducto entradaProducto = obtenerPorId(id);
        Producto producto = productoRepository.obtenerPorId(entradaProducto.getProducto().getId());

        if (entradaProducto != null && producto != null) {
            try {

                // Comienza la transacción
                // desahilita que se ejecute automaticamente la transaccion
                conn = MySQLConnection.getConnection();
                conn.setAutoCommit(false); // Comienza la transacción

                // Actualizar stock del producto
                producto.setStock(producto.getStock() - entradaProducto.getCantidad());
                productoRepository.actualizar(producto, conn);

                PreparedStatement stmt = conn.prepareStatement(sql);

                stmt.setObject(1, id);

                stmt.executeUpdate();

                conn.commit();// guarda si todo sale bien

            } catch (SQLException e) {
                if (conn != null) {
                    try {
                        conn.rollback(); // Revertir transacción en caso de error
                    } catch (SQLException rollbackEx) {
                        rollbackEx.printStackTrace();
                    }
                }
                throw e;

            }
        } else {
            System.out.println("No se encontro el producto o la entrada de producto que se queria eliminar...");
        }

    }

}
