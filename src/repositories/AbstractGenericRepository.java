package repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;

public abstract class AbstractGenericRepository<T, ID> implements GenericRepository<T, ID> {

    protected abstract String getTabla();

    protected abstract T mapeoEntidad(ResultSet rs) throws SQLException;

    @Override
    public T obtenerPorId(ID id) throws SQLException {
        String sql = "SELECT * FROM " + getTabla() + " WHERE id = ?";
        T entidad = null;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    entidad = mapeoEntidad(rs);
                }
            }
        }
        return entidad;
    }

    @Override
    public List<T> obtenerTodos() throws SQLException {
        String sql = "SELECT * FROM " + getTabla();
        List<T> entidades = new ArrayList<>();

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
    public void eliminar(ID id) throws SQLException {
        String sql = "DELETE FROM " + getTabla() + " WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public void crear(T entidad) throws SQLException {
        throw new UnsupportedOperationException("Método crear debe ser implementado en la clase específica.");
    }

    @Override
    public void actualizar(T entidad) throws SQLException {
        throw new UnsupportedOperationException("Método actualizar debe ser implementado en la clase específica.");
    }
}