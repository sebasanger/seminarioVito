package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;
import models.Caja;
import models.Pago;
import models.Reserva;
import models.Usuario;

public class PagoRepository extends AbstractGenericRepository<Pago, Integer> {

    @Override
    protected String getTabla() {
        return "pagos";
    }

    @Override
    protected Pago mapeoEntidad(ResultSet rs) throws SQLException {
        return new Pago(rs.getInt("id"), rs.getDouble("cantidad"), rs.getDate("fecha"),
                rs.getString("descripcion"), new Usuario(rs.getInt("usuarios_id")), new Caja(rs.getInt("cajas_id")),
                new Reserva(rs.getInt("reservas_id")));
    }

    @Override
    public void crear(Pago pago) throws SQLException {
        String sql = "INSERT INTO pagos (cantidad, fecha, descripcion, usuarios_id, cajas_id, reservas_id) VALUES (?,?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDouble(1, pago.getCantidad());
            stmt.setDate(2, new java.sql.Date(pago.getFecha().getTime()));
            stmt.setString(3, pago.getDescripcion());
            stmt.setInt(4, pago.getUsuario().getId());
            stmt.setInt(5, pago.getCaja().getId());
            stmt.setInt(6, pago.getReserva().getId());
            stmt.executeUpdate();
        }
    }

    // busca todos los pagos de una reserva
    public List<Pago> obtenerPagosReserva(Integer reservaId) throws SQLException {
        String sql = "SELECT * FROM pagos WHERE reservas_id = ?";
        List<Pago> entidades = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, reservaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entidades.add(mapeoEntidad(rs));
                }
            }
        }
        return entidades;
    }

}
