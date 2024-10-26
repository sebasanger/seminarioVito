package services;

import models.Categoria;
import repositories.CategoriaRepository;

public class CategoriaService extends AbstractGenericService<Categoria, Integer> {
    private CategoriaRepository categoriaRepository = new CategoriaRepository();

    @Override
    protected CategoriaRepository getRepository() {
        return categoriaRepository;
    }

}