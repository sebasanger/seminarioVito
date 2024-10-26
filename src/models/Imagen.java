package models;

public class Imagen {
    private Integer id;
    private String path;
    private String titulo;
    private String tipo;

    public Imagen() {
    }

    public Imagen(String path, String titulo, String tipo) {
        this.path = path;
        this.titulo = titulo;
        this.tipo = tipo;
    }

    public Imagen(Integer id, String path, String titulo, String tipo) {
        this.id = id;
        this.path = path;
        this.titulo = titulo;
        this.tipo = tipo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Imagen [id=" + id + ", path=" + path + ", titulo=" + titulo + ", tipo=" + tipo + "]";
    }

}
