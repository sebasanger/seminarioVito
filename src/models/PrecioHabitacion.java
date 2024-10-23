package models;

import java.util.Date;

public class PrecioHabitacion {
    private Integer id;
    private Double precio;
    private String descripcion;
    private Boolean disponible;
    private Date fechaCreacion;

    public PrecioHabitacion() {
    }

    public PrecioHabitacion(Integer id) {
        this.id = id;
    }

    public PrecioHabitacion(Double precio, String descripcion, Boolean disponible, Date fechaCreacion) {
        this.precio = precio;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.fechaCreacion = fechaCreacion;
    }

    public PrecioHabitacion(Integer id, Double precio, String descripcion, Boolean disponible, Date fechaCreacion) {
        this.id = id;
        this.precio = precio;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "PrecioHabitacion [id=" + id + ", precio=" + precio + ", descripcion=" + descripcion + ", disponible="
                + disponible + ", fechaCreacion=" + fechaCreacion + "]";
    }

}
