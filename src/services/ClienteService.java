package services;

import java.sql.SQLException;

import models.Cliente;
import repositories.ClienteRepository;

public class ClienteService extends AbstractGenericService<Cliente, Integer> {
    private ClienteRepository clienteRepository = new ClienteRepository();

    @Override
    protected ClienteRepository getRepository() {
        return clienteRepository;
    }

    // obtiene el cliente por el documento indicado
    public Cliente obtenerPorDocumento(String documento) throws SQLException {
        return this.clienteRepository.obtenerPorDocumento(documento);
    }

}