package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.Imagen;

public class ImagenRepository extends AbstractGenericRepository<Imagen, Integer> {

    @Override
    protected String getTabla() {
        return "imagenes";
    }

    @Override
    protected Imagen mapeoEntidad(ResultSet rs) throws SQLException {
        return new Imagen(rs.getInt("id"), rs.getString("path"), rs.getString("titulo"), rs.getString("tipo"));
    }

    @Override
    public void crear(Imagen imagen) throws SQLException {
        String sql = "INSERT INTO imagenes (path, titulo, tipo) VALUES (?,?,?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imagen.getPath());
            stmt.setString(2, imagen.getTitulo());
            stmt.setString(3, imagen.getTipo());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Imagen imagen) throws SQLException {
        String sql = "UPDATE imagenes SET path = ?, titulo = ?, tipo = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, imagen.getPath());
            stmt.setString(2, imagen.getTitulo());
            stmt.setString(3, imagen.getTipo());
            stmt.setInt(4, imagen.getId());

            stmt.executeUpdate();
        }
    }

}
