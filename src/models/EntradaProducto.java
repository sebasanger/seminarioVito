package models;

import java.util.Date;

public class EntradaProducto {
    private Integer id;
    private Double precioUnitario;
    private Integer cantidad;
    private Date fecha;
    private Producto producto;
    private Usuario usuario;

    public EntradaProducto() {
    }

    public EntradaProducto(Double precioUnitario, Integer cantidad, Date fecha, Producto producto,
            Usuario usuario) {
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.producto = producto;
        this.usuario = usuario;
    }

    public EntradaProducto(Integer id, Double precioUnitario, Integer cantidad, Date fecha, Integer productoId,
            Integer usuarioId) {
        this.id = id;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.producto = new Producto(productoId);
        this.usuario = new Usuario(usuarioId);
    }

    public EntradaProducto(Integer id, Double precioUnitario, Integer cantidad, Date fecha, Producto producto,
            Usuario usuario) {
        this.id = id;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.producto = producto;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
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
    public String toString() {
        return "EntradaProducto [id=" + id + ", precioUnitario=" + precioUnitario + ", cantidad=" + cantidad
                + ", fecha=" + fecha + ", producto=" + producto + ", usuario=" + usuario + "]";
    }

}
