package controllers;

import java.sql.SQLException;
import java.util.List;
import services.AbstractGenericService;

/* define un controlador generico para realizar operaciones CRUD
 * de entidades en una aplicación 
 * <T>  Tipo de la entidad.
 * <ID> Tipo del identificador de la entidad.
 */
public abstract class AbstractGenericController<T, ID> {

    // para obtener el servicio que gestiona la entidad de tipo T
    protected abstract AbstractGenericService<T, ID> getService();

    // Obtiene una entidad por su identificador
    public T obtenerPorId(ID id) throws SQLException {
        return getService().obtenerPorId(id);
    }

    // Obtiene todas las entidades disponibles en el sistema
    public List<T> obtenerTodos() throws SQLException {
        return getService().obtenerTodos();
    }

    // Crea una nueva entidad en el sistema
    public void crear(T entidad) throws SQLException {
        getService().crear(entidad);
    }

    // Actualiza una entidad existente en el sistema
    public void actualizar(T entidad) throws SQLException {
        getService().actualizar(entidad);
    }

    // Elimina una entidad del sistema dado su identificador
    public void eliminar(ID id) throws SQLException {
        getService().eliminar(id);
    }
}
