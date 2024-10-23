package models;

import java.util.Date;

public class Pago {
    private Integer id;
    private Double cantidad;
    private Date fecha;
    private String descripcion;
    private Usuario usuario;
    private Caja caja;
    private Reserva reserva;

    public Pago() {
    }

    public Pago(Double cantidad, Date fecha, String descripcion, Usuario usuario, Caja caja,
            Reserva reserva) {
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.caja = caja;
        this.reserva = reserva;
    }

    public Pago(Integer id, Double cantidad, Date fecha, String descripcion, Integer usuarioId, Integer cajaId,
            Integer reservaId) {
        this.id = id;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
        // this.usuario = usuario;
        // this.caja = caja;
        // this.reserva = reserva;
    }

    public Pago(Integer id, Double cantidad, Date fecha, String descripcion, Usuario usuario, Caja caja,
            Reserva reserva) {
        this.id = id;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.caja = caja;
        this.reserva = reserva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Caja getCaja() {
        return caja;
    }

    public void setCaja(Caja caja) {
        this.caja = caja;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

}
