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
import models.Producto;
import models.Reserva;

public class ConsumicionRepository extends AbstractGenericRepository<Consumicion, Integer> {

    private ProductoRepository productoRepository = new ProductoRepository();
    private ReservaRepository reservaRepository = new ReservaRepository();

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
        Connection conn = null;

        try {
            conn = MySQLConnection.getConnection();
            // Comienza la transacción
            // desahilita que se ejecute automaticamente la transaccion
            conn.setAutoCommit(false);

            // Actualizar stock del producto
            Producto producto = consumicion.getProducto();
            producto.setStock(producto.getStock() - consumicion.getCantidad());
            productoRepository.actualizar(producto, conn);

            // Actualizar precio total de la reserva
            Reserva reserva = consumicion.getReserva();
            Double totalAPagar = reserva.getPrecioTotal() + consumicion.getPrecioTotal();
            reservaRepository.cambiarMontos(reserva.getId(), totalAPagar, reserva.getPagadoTotal(), conn);

            // Crear la consumición
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, consumicion.getCantidad());
                stmt.setDouble(2, consumicion.getPrecioTotal());
                stmt.setDouble(3, consumicion.getPrecioUnitario());
                stmt.setDate(4, new java.sql.Date(consumicion.getFecha().getTime()));
                stmt.setInt(5, consumicion.getReserva().getId());
                stmt.setInt(6, consumicion.getProducto().getId());
                stmt.setInt(7, consumicion.getUsuario().getId());
                stmt.executeUpdate();
            }

            // Confirmar la transacción si todo se ejecuto correctamente
            conn.commit();

        } catch (SQLException e) {
            System.err.println(e);
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

    // TODO: implementar eliminacion con reestock y guardado de precios en reserva

    // obitene un monto de las consumicones realizadas por una reserva
    public Double obtenerTotalConsumcionesPorReserva(Integer reservaId) throws SQLException {
        String sql = "SELECT * FROM " + getTabla() + " WHERE reservas_id = ?";
        Double total = 0D;
        Consumicion entidad;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, reservaId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    entidad = mapeoEntidad(rs);
                    total = total + entidad.getPrecioTotal();
                }
            }
        }
        return total;
    }

    // obtiene el listado de las consumiciones de una reserva
    public List<Consumicion> obtenerConsumosReserva(Integer reservaId) throws SQLException {
        String sql = "SELECT * FROM consumiciones WHERE reservas_id = ?";
        List<Consumicion> consumiciones = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, reservaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    consumiciones.add(mapeoEntidad(rs));
                }
            }
        }
        return consumiciones;
    }

}
