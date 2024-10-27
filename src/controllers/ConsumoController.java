package controllers;

import java.sql.SQLException;
import java.util.List;

import exceptions.StockInsuficienteException;
import models.Consumicion;
import services.ConsumoService;

public class ConsumoController extends AbstractGenericController<Consumicion, Integer> {

    private ConsumoService consumoService = new ConsumoService();

    @Override
    protected ConsumoService getService() {
        return consumoService;
    }

    // no se sobrescribe el metodo de ya que cambia la firma,
    // llamando a otro metodo en el servicio y retornando mas excepciones
    public void crearConsumo(Consumicion consumicion) throws SQLException, StockInsuficienteException {
        consumoService.crearConsumo(consumicion);
    }

    // se genera un aviso de que el metodo no esta implementado
    // con la misma firma del metodo del generico
    @Override
    public void crear(Consumicion consumicion) throws SQLException {
        throw new SQLException("Metodo no implementado");
    }

    // busca todos los consumos de una reserva
    public List<Consumicion> obtenerConsumosReserva(Integer id) throws SQLException {
        return getService().obtenerConsumosReserva(id);
    }

}
