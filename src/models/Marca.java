package models;

public class Marca {
    private Integer id;
    private String marca;

    public Marca() {
    }

    public Marca(String marca) {

        this.marca = marca;
    }

    public Marca(Integer id) {
        this.id = id;
    }

    public Marca(Integer id, String marca) {
        this.id = id;
        this.marca = marca;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    @Override
    public String toString() {
        return "Marca [id=" + id + ", marca=" + marca + "]";
    }

}
