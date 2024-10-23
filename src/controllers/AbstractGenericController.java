package controllers;

import java.sql.SQLException;
import java.util.List;
import services.AbstractGenericService;

public abstract class AbstractGenericController<T, ID> {

    protected abstract AbstractGenericService<T, ID> getService();

    public T obtenerPorId(ID id) throws SQLException {
        return getService().obtenerPorId(id);
    }

    public List<T> obtenerTodos() throws SQLException {
        return getService().obtenerTodos();
    }

    public void crear(T entidad) throws SQLException {
        getService().crear(entidad);
    }

    public void actualizar(T entidad) throws SQLException {
        getService().actualizar(entidad);
    }

    public void eliminar(ID id) throws SQLException {
        getService().eliminar(id);
    }
}
