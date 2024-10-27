package repositories;

import java.sql.SQLException;
import java.util.List;

//Interfaz generica que define los métodos básicos para operaciones CRUD
//<T>  Tipo de la entidad.
//<ID> Tipo del id de la entidad.
public interface GenericRepository<T, ID> {

    // Crea una nueva entidad en la base de datos
    void crear(T entidad) throws SQLException;

    // Obtiene una entidad de la base de datos por su id
    T obtenerPorId(ID id) throws SQLException;

    // Obtiene todas las entidades de la base de datos
    List<T> obtenerTodos() throws SQLException;

    // Actualiza una entidad existente en la base de datos
    void actualizar(T entidad) throws SQLException;

    // Elimina una entidad de la base de datos dado su id
    void eliminar(ID id) throws SQLException;

}