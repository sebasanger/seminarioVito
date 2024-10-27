package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.Usuario;

public class UsuarioRepository extends AbstractGenericRepository<Usuario, Integer> {

    @Override
    protected String getTabla() {
        return "usuarios";
    }

    @Override
    protected Usuario mapeoEntidad(ResultSet rs) throws SQLException {
        return new Usuario(rs.getInt("id"), rs.getString("email"), rs.getString("password"), rs.getString("nombre"),
                rs.getString("apellido"), rs.getString("rol"));
    }

    protected Usuario mapeoEntidad(ResultSet rs, String idName) throws SQLException {
        String id = "id";
        if (idName != null) {
            id = idName;
        }

        return new Usuario(rs.getInt(id), rs.getString("email"), rs.getString("password"), rs.getString("nombre"),
                rs.getString("apellido"), rs.getString("rol"));
    }

    @Override
    public void crear(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (email, password, nombre, apellido, rol) VALUES (?,?,?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getRol());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET email = ?, password = ?, nombre = ?, apellido = ?, rol = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getPassword());
            stmt.setString(3, usuario.getNombre());
            stmt.setString(4, usuario.getApellido());
            stmt.setString(5, usuario.getRol());
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
        }
    }

    // busca el usuario por su email para realizar el login
    public Usuario obtenerPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM " + getTabla() + " WHERE email = ?";
        Usuario entidad = null;

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    entidad = mapeoEntidad(rs);
                }
            }
        }
        return entidad;
    }

}
