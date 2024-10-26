package controllers;

import models.Imagen;
import services.ImagenService;

public class ImagenController extends AbstractGenericController<Imagen, Integer> {

    private ImagenService imagenService = new ImagenService();

    @Override
    protected ImagenService getService() {
        return imagenService;
    }

}
