package services;

import models.Marca;
import repositories.MarcaRepository;

public class MarcaService extends AbstractGenericService<Marca, Integer> {
    private MarcaRepository marcaRepository = new MarcaRepository();

    @Override
    protected MarcaRepository getRepository() {
        return marcaRepository;
    }

}