package controllers;

import models.Categoria;
import services.CategoriaService;

//extendiendo  el AbstractGenericController como corresponde ya tiene la implementacion de los 
//metodos para llamar los metodos basicos de su correspondiente servicio
//permitiendo tambien una sobrescritura de los metodos

public class CategoriaController extends AbstractGenericController<Categoria, Integer> {

    private CategoriaService categoriaService = new CategoriaService();

    @Override
    protected CategoriaService getService() {
        return categoriaService;
    }

}
