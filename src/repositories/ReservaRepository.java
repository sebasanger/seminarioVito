package repositories;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.MySQLConnection;
import models.Cliente;
import models.EstadoReservaEnum;
import models.Habitacion;
import models.PrecioHabitacion;
import models.Reserva;
import models.Usuario;
import utils.DateUtils;

public class ReservaRepository extends AbstractGenericRepository<Reserva, Integer> {

    private HabitacionRepository habitacionRepository = new HabitacionRepository();
    private UsuarioRepository usuarioRepository = new UsuarioRepository();
    private PrecioHabitacionRepository precioHabitacionRepository = new PrecioHabitacionRepository();

    private static final String sqlGetAll = "SELECT *\n" + //
            "FROM reservas\n" + //
            "INNER JOIN  habitaciones ON  habitaciones.id = reservas.habitaciones_id\n" + //
            "INNER JOIN  precios_habitaciones ON  precios_habitaciones.id = reservas.precios_habitaciones_id\n" + //
            "INNER JOIN  usuarios ON  usuarios.id = reservas.usuarios_id";

    @Override
    protected String getTabla() {
        return "reservas";
    }

    @Override
    public List<Reserva> obtenerTodos() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sqlGetAll)) {

            while (rs.next()) {
                reservas.add(mapeoEntidad(rs));
            }
        }
        return reservas;
    }

    @Override
    public Reserva obtenerPorId(Integer id) throws SQLException {
        String sql = sqlGetAll + " WHERE reservas.id = ?";
        Reserva entidad = null;

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
    protected Reserva mapeoEntidad(ResultSet rs) throws SQLException {
        Habitacion habitacion = habitacionRepository.mapeoEntidad(rs, "habitaciones_id");
        PrecioHabitacion precioHabitacion = precioHabitacionRepository.mapeoEntidad(rs, "precios_habitaciones_id");
        Usuario usuario = usuarioRepository.mapeoEntidad(rs, "usuarios_id");

        return new Reserva(rs.getInt("id"), rs.getDate("checkIn"), rs.getDate("checkOut"), rs.getDate("fechaCreacion"),
                rs.getDate("fechaInicio"), rs.getDate("fechaFin"), rs.getString("origen"),
                rs.getString("destino"), rs.getDouble("precioDiario"), rs.getDouble("precioTotal"),
                rs.getDouble("pagadoTotal"), rs.getString("estado"),
                habitacion,
                precioHabitacion,
                usuario);
    }

    @Override
    public void crear(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO reservas (checkIn, checkOut, fechaCreacion, fechaInicio, fechaFin, origen, destino, precioDiario, precioTotal, pagadoTotal, estado, habitaciones_id, precios_habitaciones_id, usuarios_id ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;

        try {

            conn = MySQLConnection.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            stmt.setDate(1, DateUtils.transformDateUtilToSql(reserva.getCheckIn()));
            stmt.setDate(2, DateUtils.transformDateUtilToSql(reserva.getCheckOut()));
            stmt.setDate(3, DateUtils.transformDateUtilToSql(reserva.getFechaCreacion()));
            stmt.setDate(4, DateUtils.transformDateUtilToSql(reserva.getFechaInicio()));
            stmt.setDate(5, DateUtils.transformDateUtilToSql(reserva.getFechaFin()));

            stmt.setString(6, reserva.getOrigen());
            stmt.setString(7, reserva.getDestino());

            stmt.setDouble(8, reserva.getPrecioDiario() != null ? reserva.getPrecioDiario() : 0);
            stmt.setDouble(9, reserva.getPrecioTotal() != null ? reserva.getPrecioTotal() : 0);
            stmt.setDouble(10, reserva.getPagadoTotal() != null ? reserva.getPagadoTotal() : 0);

            stmt.setString(11, reserva.getEstado());

            stmt.setInt(12, reserva.getHabitacion().getId());
            stmt.setInt(13, reserva.getPrecioHabitacion().getId());
            stmt.setInt(14, reserva.getUsuario().getId());

            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int reservaId = generatedKeys.getInt(1);

                // se vuelven a agregar los clientes a la reserva por si se cambiaron
                this.insertarClientesReserva(conn, reserva.getClientes(), reservaId);

            } else {
                throw new SQLException("Error al obtener el ID de la reserva insertada.");
            }

            this.cambiarEstadoHabitacion(reserva.getHabitacion(), reserva.getEstado(), conn);

            System.out.println("Reserva generada correctamente");
            System.out.println(reserva);
            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir transacción en caso de error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;

        }
    }

    private void eliminarClientesReserva(Connection conn, int reservaId) throws SQLException {
        String sqlClientesReservas = "DELETE FROM reservas_clientes WHERE reservas_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sqlClientesReservas)) {
            stmt.setInt(1, reservaId);
            stmt.executeUpdate();
        }
    }

    private void insertarClientesReserva(Connection conn, List<Cliente> clientes, int reservaId) throws SQLException {
        String sqlClientesReservas = "INSERT INTO reservas_clientes (clientes_id, reservas_id) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sqlClientesReservas)) {
            for (Cliente cliente : clientes) {
                stmt.setInt(1, cliente.getId());
                stmt.setInt(2, reservaId);
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void actualizar(Reserva reserva) throws SQLException {
        actualizar(reserva, null); // Llama al método sobrecargado con conexión opcional
    }

    public void actualizar(Reserva reserva, Connection conn) throws SQLException {
        String sql = "UPDATE reservas SET checkIn = ?, checkOut = ?, fechaCreacion = ?, fechaInicio = ?, fechaFin = ?, origen = ?, destino = ?, precioDiario = ?, precioTotal = ?, pagadoTotal = ?, estado = ?, habitaciones_id = ?, precios_habitaciones_id = ?, usuarios_id = ? WHERE id = ?";
        boolean cerrarConexion = false;

        if (conn == null) {
            conn = MySQLConnection.getConnection();
            cerrarConexion = true;
        }

        try {
            conn.setAutoCommit(false);
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setDate(1, DateUtils.transformDateUtilToSql(reserva.getCheckIn()));
            stmt.setDate(2, DateUtils.transformDateUtilToSql(reserva.getCheckOut()));
            stmt.setDate(3, DateUtils.transformDateUtilToSql(reserva.getFechaCreacion()));
            stmt.setDate(4, DateUtils.transformDateUtilToSql(reserva.getFechaInicio()));
            stmt.setDate(5, DateUtils.transformDateUtilToSql(reserva.getFechaFin()));

            stmt.setString(6, reserva.getOrigen());
            stmt.setString(7, reserva.getDestino());

            stmt.setDouble(8, reserva.getPrecioDiario() != null ? reserva.getPrecioDiario() : 0);
            stmt.setDouble(9, reserva.getPrecioTotal() != null ? reserva.getPrecioTotal() : 0);
            stmt.setDouble(10, reserva.getPagadoTotal() != null ? reserva.getPagadoTotal() : 0);

            stmt.setString(11, reserva.getEstado());

            stmt.setInt(12, reserva.getHabitacion().getId());
            stmt.setInt(13, reserva.getPrecioHabitacion().getId());
            stmt.setInt(14, reserva.getUsuario().getId());
            stmt.setInt(15, reserva.getId());

            stmt.executeUpdate();

            this.eliminarClientesReserva(conn, reserva.getId());// se elimina para actualizar los clientes de la reserva

            insertarClientesReserva(conn, reserva.getClientes(), reserva.getId());

            this.cambiarEstadoHabitacion(reserva.getHabitacion(), reserva.getEstado(), conn);

            conn.commit();
        } finally {
            try {
                conn.rollback(); // Revertir transacción en caso de error
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            if (cerrarConexion && conn != null) {
                conn.close();
            }
        }
    }

    public List<Reserva> obtenerReservasPorEstado(String status) throws SQLException {
        String sql = sqlGetAll + " WHERE estado = ?";
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservas.add(mapeoEntidad(rs));
                }
            }

        }
        return reservas;
    }

    public List<Reserva> obtenerReservasPorEstadoYFecha(String status, Date fechaActual, Boolean porFechaFin)
            throws SQLException {
        String sql = "";
        if (porFechaFin == true) {
            sql = sqlGetAll + " WHERE estado = ? AND fechaFin = ?";
        } else {
            sql = sqlGetAll + " WHERE estado = ? AND fechaInicio = ?";
        }
        List<Reserva> reservas = new ArrayList<>();

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setDate(2, fechaActual);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    reservas.add(mapeoEntidad(rs));
                }
            }

        }
        return reservas;
    }

    public void cambiarEstadoReserva(Integer reservaId, EstadoReservaEnum estado) throws SQLException {
        String sql = "UPDATE reservas SET  estado = ? WHERE id = ?";
        Reserva reserva = obtenerPorId(reservaId);
        Connection conn = null;
        Habitacion habitacion = habitacionRepository.obtenerPorId(reserva.getHabitacion().getId());

        try {
            conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            conn.setAutoCommit(false);

            this.cambiarEstadoHabitacion(habitacion, estado.getEstado(), conn);

            stmt.setString(1, estado.getEstado());
            stmt.setInt(2, reservaId);
            stmt.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Revertir transacción en caso de error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            throw e;

        }
    }

    public void cambiarMontos(Integer reservaId, Double totalPagar, Double totalPagado) throws SQLException {
        cambiarMontos(reservaId, totalPagar, totalPagado, null);
    }

    public void cambiarMontos(Integer reservaId, Double totalPagar, Double totalPagado, Connection conn)
            throws SQLException {
        String sql = "UPDATE reservas SET precioTotal = ?, pagadoTotal = ? WHERE id = ?";
        boolean cerrarConexion = false;

        if (conn == null) {
            conn = MySQLConnection.getConnection();
            cerrarConexion = true;
        }

        try {
            conn = MySQLConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            conn.setAutoCommit(false);

            stmt.setDouble(1, totalPagar);
            stmt.setDouble(2, totalPagado);
            stmt.setInt(3, reservaId);
            stmt.executeUpdate();

            conn.commit();
        } finally {
            // Cierra la conexión solo si fue creada internamente en este método
            if (cerrarConexion && conn != null) {
                conn.close();
            }
        }
    }

    private void cambiarEstadoHabitacion(Habitacion habitacion, String estado, Connection conn)
            throws SQLException {
        if (estado.equals(EstadoReservaEnum.ACTIVA.getEstado())) {
            habitacion.setdisponible(false);
        } else {
            habitacion.setdisponible(true);
        }

        habitacionRepository.actualizar(habitacion, conn);
    }

}
