package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.Categoria;

public class CategoriaRepository extends AbstractGenericRepository<Categoria, Integer> {

    @Override
    protected String getTabla() {
        return "categorias";
    }

    @Override
    protected Categoria mapeoEntidad(ResultSet rs) throws SQLException {
        return new Categoria(rs.getInt("id"), rs.getString("categoria"));
    }

    @Override
    public void crear(Categoria categoria) throws SQLException {
        String sql = "INSERT INTO categorias (categoria) VALUES (?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getCategoria());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Categoria categoria) throws SQLException {
        String sql = "UPDATE categorias SET categoria = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getCategoria());
            stmt.setInt(2, categoria.getId());
            stmt.executeUpdate();
        }
    }

}
