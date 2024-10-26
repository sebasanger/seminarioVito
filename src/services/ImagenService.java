package services;

import models.Imagen;
import repositories.ImagenRepository;

public class ImagenService extends AbstractGenericService<Imagen, Integer> {
    private ImagenRepository imagenRepository = new ImagenRepository();

    @Override
    protected ImagenRepository getRepository() {
        return imagenRepository;
    }

}