package services;

import models.Categoria;
import repositories.CategoriaRepository;

//solo extendiendo AbstractGenericService y hace todos los llamados requeridos ya que no se requirio una sobrescritura de los metodos
public class CategoriaService extends AbstractGenericService<Categoria, Integer> {
    private CategoriaRepository categoriaRepository = new CategoriaRepository();

    @Override
    protected CategoriaRepository getRepository() {
        return categoriaRepository;
    }

}