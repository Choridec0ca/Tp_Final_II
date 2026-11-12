public enum Estado_Vehiculo {
    DISPONIBLE("Disponible"),
    ALQUILADO("Alquilado por un cliente"),
    EN_LIMPIEZA("En limpieza"),
    EN_REPARACION("En reparacion");

    private String motivo;

    Estado_Vehiculo(String motivo) {
        this.motivo = motivo;
    }

    public String getMotivo() {
        return motivo;
    }
}
