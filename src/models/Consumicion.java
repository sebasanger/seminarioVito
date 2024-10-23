package models;

import java.util.Date;

public class Consumicion {
    private Integer id;
    private Integer cantidad;
    private Double precioTotal;
    private Double precioUnitario;
    private Date fecha;
    private Reserva reserva;
    private Producto producto;
    private Usuario usuario;

    public Consumicion() {
    }

    public Consumicion(Integer cantidad, Double precioTotal, Double precioUnitario, Date fecha,
            Reserva reserva, Producto producto, Usuario usuario) {
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
        this.reserva = reserva;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Consumicion(Integer id, Integer cantidad, Double precioTotal, Double precioUnitario, Date fecha,
            Integer reservaId, Integer productoId, Integer usuarioId) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
        this.reserva = new Reserva(reservaId);
        this.producto = new Producto(productoId);
        this.usuario = new Usuario(usuarioId);
    }

    public Consumicion(Integer id, Integer cantidad, Double precioTotal, Double precioUnitario, Date fecha,
            Reserva reserva, Producto producto, Usuario usuario) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioTotal = precioTotal;
        this.precioUnitario = precioUnitario;
        this.fecha = fecha;
        this.reserva = reserva;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Consumicion other = (Consumicion) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Consumicion [id=" + id + ", cantidad=" + cantidad + ", precioTotal=" + precioTotal + ", precioUnitario="
                + precioUnitario + ", fecha=" + fecha + ", reserva=" + reserva + ", producto=" + producto + ", usuario="
                + usuario + "]";
    }

}
