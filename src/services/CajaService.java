package services;

import java.sql.SQLException;
import java.util.List;

import exceptions.CajaNoAbiertaException;
import models.Caja;
import repositories.CajaRepository;
import repositories.UsuarioRepository;

public class CajaService extends AbstractGenericService<Caja, Integer> {
    private CajaRepository cajaRepository = new CajaRepository();

    @Override
    protected CajaRepository getRepository() {
        return cajaRepository;
    }

    @Override
    public List<Caja> obtenerTodos() throws SQLException {
        UsuarioRepository usuarioRepository = new UsuarioRepository();

        List<Caja> cajas = this.getRepository().obtenerTodos();

        for (Caja caja : cajas) {
            caja.setUsuario(usuarioRepository.obtenerPorId(caja.getUsuario().getId()));

        }

        return cajas;
    }

    public Caja obtenerCajaActiva() throws SQLException, CajaNoAbiertaException {
        Caja cajaAbierta = this.cajaRepository.obtenerCajaActiva();
        if (cajaAbierta == null) {
            throw new CajaNoAbiertaException();
        }
        return cajaAbierta;
    }

}