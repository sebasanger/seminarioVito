package services;

import java.sql.SQLException;
import java.util.List;
import repositories.AbstractGenericRepository;

//proporciona servicios genericos para gestionar entidades en una capa de servicio
//generando asi una llamada mas facil a los repositories con sus metodos basicos, pero tambien puede ser sobrescrito de ser necesario
//funcionando asi como interfaz e implementando los metodos basicos al mismo tiempo
//<T>  Tipo de la entidad
//<ID> Tipo del identificador de la entidad
public abstract class AbstractGenericService<T, ID> {

    // para obtener el repositorio específico que gestiona la entidad de tipo T
    protected abstract AbstractGenericRepository<T, ID> getRepository();

    // Obtiene una entidad por su identificador
    public T obtenerPorId(ID id) throws SQLException {
        return getRepository().obtenerPorId(id);
    }

    // obtiene todas las entidades de la entidad buscada
    public List<T> obtenerTodos() throws SQLException {
        return getRepository().obtenerTodos();
    }

    // genera la entidad
    public void crear(T entidad) throws SQLException {
        getRepository().crear(entidad);
    }

    // actualiza la entidad
    public void actualizar(T entidad) throws SQLException {
        getRepository().actualizar(entidad);
    }

    // elimina la entidad
    public void eliminar(ID id) throws SQLException {
        getRepository().eliminar(id);
    }
}
