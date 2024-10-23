package services;

import java.sql.SQLException;
import java.util.List;
import repositories.AbstractGenericRepository;

public abstract class AbstractGenericService<T, ID> {

    protected abstract AbstractGenericRepository<T, ID> getRepository();

    public T obtenerPorId(ID id) throws SQLException {
        return getRepository().obtenerPorId(id);
    }

    public List<T> obtenerTodos() throws SQLException {
        return getRepository().obtenerTodos();
    }

    public void crear(T entidad) throws SQLException {
        getRepository().crear(entidad);
    }

    public void actualizar(T entidad) throws SQLException {
        getRepository().actualizar(entidad);
    }

    public void eliminar(ID id) throws SQLException {
        getRepository().eliminar(id);
    }
}
