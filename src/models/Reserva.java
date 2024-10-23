package models;

import java.util.Date;
import java.util.List;

public class Reserva {
    private Integer id;
    private Date checkIn;
    private Date checkOut;
    private Date fechaCreacion;
    private Date fechaInicio;
    private Date fechaFin;
    private String origen;
    private String destino;
    private Double precioDiario;
    private Double precioTotal;
    private Double pagadoTotal;
    private String estado;

    private List<Cliente> clientes;
    private Habitacion habitacion;
    private PrecioHabitacion precioHabitacion;

    private Integer precioHabitacionId;
    private Integer habitacionId;
    private Integer usuarioId;

    private Usuario usuario;

    public Reserva() {
    }

    public Reserva(Integer id) {
        this.id = id;
    }

    public Reserva(Date checkIn, Date checkOut, Date fechaCreacion, Date fechaInicio, Date fechaFin,
            String origen, String destino, Double precioDiario, Double precioTotal, Double pagadoTotal, String estado,
            List<Cliente> clientes, Habitacion habitacion, PrecioHabitacion precioHabitacion, Usuario usuario) {
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.origen = origen;
        this.destino = destino;
        this.precioDiario = precioDiario;
        this.precioTotal = precioTotal;
        this.pagadoTotal = pagadoTotal;
        this.estado = estado;
        this.clientes = clientes;
        this.habitacion = habitacion;
        this.precioHabitacion = precioHabitacion;
        this.usuario = usuario;
    }

    public Reserva(Integer id, Date checkIn, Date checkOut, Date fechaCreacion, Date fechaInicio, Date fechaFin,
            String origen, String destino, Double precioDiario, Double precioTotal, Double pagadoTotal, String estado,
            Integer habitacionId, Integer precioHabitacionId, Integer usuarioId) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.origen = origen;
        this.destino = destino;
        this.precioDiario = precioDiario;
        this.precioTotal = precioTotal;
        this.pagadoTotal = pagadoTotal;
        this.estado = estado;
        this.habitacionId = habitacionId;
        this.precioHabitacionId = precioHabitacionId;
        this.usuarioId = usuarioId;
    }

    public Reserva(Integer id, Date checkIn, Date checkOut, Date fechaCreacion, Date fechaInicio, Date fechaFin,
            String origen, String destino, Double precioDiario, Double precioTotal, Double pagadoTotal, String estado,
            List<Cliente> clientes, Habitacion habitacion, PrecioHabitacion precioHabitacion, Usuario usuario) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.fechaCreacion = fechaCreacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.origen = origen;
        this.destino = destino;
        this.precioDiario = precioDiario;
        this.precioTotal = precioTotal;
        this.pagadoTotal = pagadoTotal;
        this.estado = estado;
        this.clientes = clientes;
        this.habitacion = habitacion;
        this.precioHabitacion = precioHabitacion;
        this.usuario = usuario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Double getPrecioDiario() {
        return precioDiario;
    }

    public void setPrecioDiario(Double precioDiario) {
        this.precioDiario = precioDiario;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public Double getPagadoTotal() {
        return pagadoTotal;
    }

    public void setPagadoTotal(Double pagadoTotal) {
        this.pagadoTotal = pagadoTotal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion habitacion) {
        this.habitacion = habitacion;
    }

    public PrecioHabitacion getPrecioHabitacion() {
        return precioHabitacion;
    }

    public void setPrecioHabitacion(PrecioHabitacion precioHabitacion) {
        this.precioHabitacion = precioHabitacion;
    }

    public Integer getHabitacionId() {
        return habitacionId;
    }

    public void setHabitacionId(Integer habitacionId) {
        this.habitacionId = habitacionId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
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
        Reserva other = (Reserva) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Reserva [id=" + id + ", checkIn=" + checkIn + ", checkOut=" + checkOut + ", fechaCreacion="
                + fechaCreacion + ", fechaInicio=" + fechaInicio + ", fechaFin=" + fechaFin + ", origen=" + origen
                + ", destino=" + destino + ", precioDiario=" + precioDiario + ", precioTotal=" + precioTotal
                + ", pagadoTotal=" + pagadoTotal + ", estado=" + estado + ", clientes=" + clientes + ", habitacion="
                + habitacion + ", precioHabitacion=" + precioHabitacion + ", precioHabitacionId=" + precioHabitacionId
                + ", habitacionId=" + habitacionId + ", usuarioId=" + usuarioId + ", usuario=" + usuario + "]";
    }

    public Integer getPrecioHabitacionId() {
        return precioHabitacionId;
    }

    public void setPrecioHabitacionId(Integer precioHabitacionId) {
        this.precioHabitacionId = precioHabitacionId;
    }

}
