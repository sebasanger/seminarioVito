package models;

public enum EstadoReservaEnum {
    PENDIENTE("PENDIENTE"),
    FINALIZADA("FINALIZADA"),
    ACTIVA("ACTIVA"),
    ELIMINADA("ELIMINADA");

    private final String estado;

    // Constructor del enum
    EstadoReservaEnum(String estado) {
        this.estado = estado;
    }

    // Método para obtener el valor del estado
    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return estado;
    }
}