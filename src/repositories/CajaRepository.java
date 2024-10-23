package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.Caja;

public class CajaRepository extends AbstractGenericRepository<Caja, Integer> {

    @Override
    protected String getTabla() {
        return "cajas";
    }

    @Override
    protected Caja mapeoEntidad(ResultSet rs) throws SQLException {
        return new Caja(rs.getInt("id"), rs.getBoolean("activa"), rs.getDouble("montoApertura"),
                rs.getDouble("montoCierre"), rs.getDate("fechaApertura"), rs.getDate("fechaCierre"),
                rs.getInt("usuarios_id"));
    }

    @Override
    public void crear(Caja caja) throws SQLException {
        String sql = "INSERT INTO cajas (activa, montoApertura, montoCierre, fechaApertura, fechaCierre, usuarios_id) VALUES (?,?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, caja.getActiva());
            stmt.setDouble(2, caja.getMontoApertura());
            stmt.setDouble(3, caja.getMontoCierre());
            stmt.setDate(4, new java.sql.Date(caja.getFechaApertura().getTime()));
            stmt.setDate(5, new java.sql.Date(caja.getFechaCierre().getTime()));
            stmt.setInt(6, caja.getUsuario().getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Caja caja) throws SQLException {
        String sql = "UPDATE cajas SET activa = ?, montoApertura = ?, montoCierre = ?, fechaApertura = ?, fechaCierre = ?, usuarios_id = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setBoolean(1, caja.getActiva());
            stmt.setDouble(2, caja.getMontoApertura());
            stmt.setDouble(3, caja.getMontoCierre());
            stmt.setDate(4, new java.sql.Date(caja.getFechaApertura().getTime()));
            stmt.setDate(5, new java.sql.Date(caja.getFechaCierre().getTime()));
            stmt.setInt(6, caja.getUsuario().getId());
            stmt.setInt(7, caja.getId());
            stmt.executeUpdate();
        }
    }

}