package models;

import java.util.List;

public class Habitacion {
    private Integer id;
    private String numeroHabitacion;
    private Boolean disponible;
    private Boolean habilitada;
    private Integer piso;
    private Integer camasSingles;
    private Integer camasDobles;
    private Integer capacidad;
    private List<Imagen> imagenes;

    public Habitacion() {
    }

    public Habitacion(Integer id) {
        this.id = id;
    }

    public Habitacion(Integer id, String numeroHabitacion, Boolean disponible, Boolean habilitada, Integer piso,
            Integer camasSingles, Integer camasDobles, Integer capacidad) {
        this.id = id;
        this.numeroHabitacion = numeroHabitacion;
        this.disponible = disponible;
        this.habilitada = habilitada;
        this.piso = piso;
        this.camasSingles = camasSingles;
        this.camasDobles = camasDobles;
        this.capacidad = capacidad;
    }

    public Habitacion(String numeroHabitacion, Boolean disponible, Boolean habilitada, Integer piso,
            Integer camasSingles, Integer camasDobles, Integer capacidad, List<Imagen> imagenes) {
        this.numeroHabitacion = numeroHabitacion;
        this.disponible = disponible;
        this.habilitada = habilitada;
        this.piso = piso;
        this.camasSingles = camasSingles;
        this.camasDobles = camasDobles;
        this.capacidad = capacidad;
        this.imagenes = imagenes;
    }

    public Habitacion(Integer id, String numeroHabitacion, Boolean disponible, Boolean habilitada, Integer piso,
            Integer camasSingles, Integer camasDobles, Integer capacidad, List<Imagen> imagenes) {
        this.id = id;
        this.numeroHabitacion = numeroHabitacion;
        this.disponible = disponible;
        this.habilitada = habilitada;
        this.piso = piso;
        this.camasSingles = camasSingles;
        this.camasDobles = camasDobles;
        this.capacidad = capacidad;
        this.imagenes = imagenes;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroHabitacion() {
        return numeroHabitacion;
    }

    public void setNumeroHabitacion(String numeroHabitacion) {
        this.numeroHabitacion = numeroHabitacion;
    }

    public Boolean getdisponible() {
        return disponible;
    }

    public void setdisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Boolean getHabilitada() {
        return habilitada;
    }

    public void setHabilitada(Boolean habilitada) {
        this.habilitada = habilitada;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public Integer getCamasSingles() {
        return camasSingles;
    }

    public void setCamasSingles(Integer camasSingles) {
        this.camasSingles = camasSingles;
    }

    public Integer getCamasDobles() {
        return camasDobles;
    }

    public void setCamasDobles(Integer camasDobles) {
        this.camasDobles = camasDobles;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    public List<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    @Override
    public String toString() {
        return "Habitacion [id=" + id + ", numeroHabitacion=" + numeroHabitacion + ", disponible=" + disponible
                + ", habilitada=" + habilitada + ", piso=" + piso + ", camasSingles=" + camasSingles + ", camasDobles="
                + camasDobles + ", capacidad=" + capacidad + ", imagenes=" + imagenes + "]";
    }

}
