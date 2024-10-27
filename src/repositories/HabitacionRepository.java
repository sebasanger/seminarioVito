package repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;
import models.Habitacion;

public class HabitacionRepository extends AbstractGenericRepository<Habitacion, Integer> {

    @Override
    protected String getTabla() {
        return "habitaciones";
    }

    @Override
    protected Habitacion mapeoEntidad(ResultSet rs) throws SQLException {
        return mapeoEntidad(rs, null);
    }

    protected Habitacion mapeoEntidad(ResultSet rs, String idName) throws SQLException {
        String id = "id";
        if (idName == null) {
            id = idName;
        }
        return new Habitacion(rs.getInt(id), rs.getString("numeroHabitacion"), rs.getBoolean("disponible"),
                rs.getBoolean("habilitada"),
                rs.getInt("piso"), rs.getInt("camasSingles"), rs.getInt("camasDobles"), rs.getInt("capacidad"));
    }

    @Override
    public void crear(Habitacion habitacion) throws SQLException {
        String sql = "INSERT INTO habitaciones (numeroHabitacion, disponible, habilitada, piso, camasSingles, camasDobles, capacidad) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, habitacion.getNumeroHabitacion());
            stmt.setBoolean(2, habitacion.getdisponible());
            stmt.setBoolean(3, habitacion.getHabilitada());
            stmt.setInt(4, habitacion.getPiso());
            stmt.setInt(5, habitacion.getCamasSingles());
            stmt.setInt(6, habitacion.getCamasDobles());
            stmt.setInt(7, habitacion.getCapacidad());
            stmt.executeUpdate();
        }
    }

    // para sobresecribir el llamado normal y mandarlo como sin conexion previa
    @Override
    public void actualizar(Habitacion habitacion) throws SQLException {
        actualizar(habitacion, null);
    }

    public void actualizar(Habitacion habitacion, Connection conn) throws SQLException {
        String sql = "UPDATE habitaciones SET numeroHabitacion = ?, disponible = ?, habilitada = ?, piso = ? , camasSingles = ? , camasDobles = ? , capacidad = ?  WHERE id = ?";
        // Variable para manejar el cierre opcional de la conexión por si se usa el
        // metodo dentro de otra conexion
        boolean cerrarConexion = false;

        // Si no se proporciona una conexion, crea una nueva y marca para cierre
        if (conn == null) {
            conn = MySQLConnection.getConnection();
            cerrarConexion = true;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, habitacion.getNumeroHabitacion());
            stmt.setBoolean(2, habitacion.getdisponible());
            stmt.setBoolean(3, habitacion.getHabilitada());
            stmt.setInt(4, habitacion.getPiso());
            stmt.setInt(5, habitacion.getCamasSingles());
            stmt.setInt(6, habitacion.getCamasDobles());
            stmt.setInt(7, habitacion.getCapacidad());
            stmt.setInt(8, habitacion.getId());
            stmt.executeUpdate();
        } finally {
            // Cierra la conexion solo si fue creada internamente en este metodo
            if (cerrarConexion && conn != null) {
                conn.close();
            }
        }
    }

    // hace la busqueda de las habitaciones libres entre un rago de fechas
    // para lo cual evalua las reservas que se tengan en esas fechas
    // si en esa habitacion se tiene una reserva para la fecha no se la agrega en la
    // lista
    public List<Habitacion> obtenerHabitacionesLibres(Date fechaInicio, Date fechaFin,
            Integer capacidad)
            throws SQLException {
        String sql = "SELECT hab.id, hab.numeroHabitacion, hab.capacidad, hab.disponible, hab.piso, hab.camasSingles, hab.camasDobles, hab.habilitada \n"
                + //
                "FROM hoteldb.habitaciones hab \n" + //
                "LEFT JOIN hoteldb.reservas res ON res.habitaciones_id = hab.id\n" + //
                "AND (\n" + //
                "\t(res.fechaInicio BETWEEN ? AND ?) \n" + //
                "\tOR\n" + //
                "\t(res.fechaFin BETWEEN ? AND ?)\n" + //
                "\tOR\n" + //
                "    (? BETWEEN res.fechaInicio AND res.fechaFin)\n" + //
                "    OR\n" + //
                "    (? BETWEEN res.fechaInicio AND res.fechaFin)\n" + //
                ")\n" + //
                "WHERE hab.capacidad >= ? AND res.id IS NULL";
        List<Habitacion> habitaciones = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, fechaInicio); // Primer rango de fechaInicio
            stmt.setDate(2, fechaFin); // Primer rango de fechaFin
            stmt.setDate(3, fechaInicio); // Segundo rango de fechaInicio
            stmt.setDate(4, fechaFin); // Segundo rango de fechaFin
            stmt.setDate(5, fechaInicio); // Fecha dentro de los rangos
            stmt.setDate(6, fechaFin); // Fecha dentro de los rangos
            stmt.setInt(7, capacidad); // Capacidad minima

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    habitaciones.add(mapeoEntidad(rs));
                }
            }
        }
        return habitaciones;
    }

}
