package controllers;

import java.sql.SQLException;

import models.Cliente;
import services.ClienteService;

public class ClienteController extends AbstractGenericController<Cliente, Integer> {

    private ClienteService clienteService = new ClienteService();

    @Override
    protected ClienteService getService() {
        return clienteService;
    }

    ////busca el cliente por su documento
    public Cliente obtenerPorDocumento(String documento) throws SQLException {
        System.out.println(documento);
        return this.clienteService.obtenerPorDocumento(documento);
    }

}
