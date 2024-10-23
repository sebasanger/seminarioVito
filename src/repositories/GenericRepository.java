package repositories;

import java.sql.SQLException;
import java.util.List;

public interface GenericRepository<T, ID> {
    // Crear
    void crear(T entidad) throws SQLException;

    // Leer por ID
    T obtenerPorId(ID id) throws SQLException;

    // Leer todos
    List<T> obtenerTodos() throws SQLException;

    // Actualizar
    void actualizar(T entidad) throws SQLException;

    // Eliminar
    void eliminar(ID id) throws SQLException;
}