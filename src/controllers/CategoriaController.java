package controllers;

import models.Categoria;
import services.CategoriaService;

public class CategoriaController extends AbstractGenericController<Categoria, Integer> {

    private CategoriaService categoriaService = new CategoriaService();

    @Override
    protected CategoriaService getService() {
        return categoriaService;
    }

}
