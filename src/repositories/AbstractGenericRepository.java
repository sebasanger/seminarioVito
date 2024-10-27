package repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;

/*
 * implementa el repositorio generico para operaciones CRUD 
 * en una entidad de tipo T utilizando un identificador de tipo ID
 * <T>  Tipo de la entidad
 * <ID> Tipo del identificador de la entidad
 * para evitar tener que reescribir los metodos que se repiten entre entidades
 */
public abstract class AbstractGenericRepository<T, ID> implements GenericRepository<T, ID> {

    // debe ser implementado para devolver el nombre de la tabla correspondiente a
    // la entidad
    protected abstract String getTabla();

    // debe ser implementado para mapear un ResultSet a una entidad T
    protected abstract T mapeoEntidad(ResultSet rs) throws SQLException;

    // Obtiene una entidad de la base de datos por su id
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

    // Obtiene todas las entidades de la base de datos
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

    // Elimina una entidad de la base de datos dado su id
    @Override
    public void eliminar(ID id) throws SQLException {
        String sql = "DELETE FROM " + getTabla() + " WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    // Crea una entidad en la base de datos
    // Debe ser implementado en las subclases
    @Override
    public void crear(T entidad) throws SQLException {
        throw new UnsupportedOperationException("Método crear debe ser implementado en la clase específica.");
    }

    // Actualiza una entidad en la base de datos.
    // Debe ser implementado en las subclases
    @Override
    public void actualizar(T entidad) throws SQLException {
        throw new UnsupportedOperationException("Método actualizar debe ser implementado en la clase específica.");
    }
}