package repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.MySQLConnection;
import models.Marca;

public class MarcaRepository extends AbstractGenericRepository<Marca, Integer> {

    @Override
    protected String getTabla() {
        return "marcas";
    }

    @Override
    protected Marca mapeoEntidad(ResultSet rs) throws SQLException {
        return new Marca(rs.getInt("id"), rs.getString("marca"));
    }

    @Override
    public void crear(Marca marca) throws SQLException {
        String sql = "INSERT INTO marcas (marca) VALUES (?)";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca.getMarca());
            stmt.executeUpdate();
        }
    }

    @Override
    public void actualizar(Marca marca) throws SQLException {
        String sql = "UPDATE marcas SET marca = ? WHERE id = ?";

        try (Connection conn = MySQLConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca.getMarca());
            stmt.setInt(2, marca.getId());
            stmt.executeUpdate();
        }
    }

}
