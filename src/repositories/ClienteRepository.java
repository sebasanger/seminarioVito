package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;
import models.Cliente;

public class ClienteRepository extends AbstractGenericRepository<Cliente, Integer> {

    @Override
    protected String getTabla() {
        return "clientes";
    }

    @Override
    protected Cliente mapeoEntidad(ResultSet rs) throws SQLException {
        return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getString("email"),
                rs.getString("documento"));
    }

    @Override
    public void crear(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO clientes (nombre, apellido, email, documento) VALUES (?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getDocumento());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Cliente cliente) throws SQLException {
        String sql = "UPDATE clientes SET nombre = ?, apellido = ?, email = ?, documento = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getApellido());
            stmt.setString(3, cliente.getEmail());
            stmt.setString(4, cliente.getDocumento());
            stmt.setInt(5, cliente.getId());
            stmt.executeUpdate();
        }
    }

    public List<Cliente> obtenerClientesReserva(Integer reservaId) throws SQLException {
        String sql = "SELECT * FROM reservas_clientes rc INNER JOIN clientes c ON rc.clientes_id = c.id  WHERE rc.reservas_id = ?";
        List<Cliente> entidades = new ArrayList<>();

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

    public Cliente obtenerPorDocumento(String documento) throws SQLException {
        String sql = "SELECT * FROM " + getTabla() + " WHERE documento = ?";
        Cliente entidad = null;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, documento);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    entidad = mapeoEntidad(rs);
                }
            }
        }
        return entidad;
    }

}
