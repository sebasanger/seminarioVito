package models;

public enum EstadoReservaEnum {
    PENDIENTE("pendiente"),
    FINALIZADA("finalizada"),
    ACTIVA("activa"),
    ELIMINADA("eliminada");

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