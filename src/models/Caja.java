package models;

import java.util.Date;

public class Caja {
    private Integer id;
    private Boolean activa;
    private Double montoApertura;
    private Double montoCierre;
    private Date fechaApertura;
    private Date fechaCierre;
    private Usuario usuario;

    public Caja() {
    }

    public Caja(Integer id) {
        this.id = id;
    }

    public Caja(Boolean activa, Double montoApertura, Double montoCierre, Date fechaApertura,
            Date fechaCierre, Usuario usuario) {
        this.activa = activa;
        this.montoApertura = montoApertura;
        this.montoCierre = montoCierre;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.usuario = usuario;
    }

    public Caja(Integer id, Boolean activa, Double montoApertura, Double montoCierre, Date fechaApertura,
            Date fechaCierre, Usuario usuario) {
        this.id = id;
        this.activa = activa;
        this.montoApertura = montoApertura;
        this.montoCierre = montoCierre;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }

    public Double getMontoApertura() {
        return montoApertura;
    }

    public void setMontoApertura(Double montoApertura) {
        this.montoApertura = montoApertura;
    }

    public Double getMontoCierre() {
        return montoCierre;
    }

    public void setMontoCierre(Double montoCierre) {
        this.montoCierre = montoCierre;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Caja [id=" + id + ", activa=" + activa + ", montoApertura=" + montoApertura + ", montoCierre="
                + montoCierre + ", fechaApertura=" + fechaApertura + ", fechaCierre=" + fechaCierre + ", usuario="
                + usuario + "]";
    }

}
