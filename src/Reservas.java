import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
public class Reservas implements Identificable<Integer> {

    private int idReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Float costoTotal;
    private boolean estaPagada;
    private String vehiculoPatente;
    private String clienteDni;

    public Reservas( LocalDate fechaInicio, LocalDate fechaFin, Float costoTotal, Boolean estaPagada, String vehiculoPatente, String clienteDni) throws Valores_Invalidos {
        if (fechaInicio == null || fechaFin == null) {
            throw new Valores_Invalidos("Las fechas de inicio y fin no pueden ser nulas...");
        }
        if (vehiculoPatente == null) {
            throw new Valores_Invalidos("La patente del vehículo no puede estar vacía...");
        }
        if (clienteDni == null) {
            throw new Valores_Invalidos("El DNI del cliente no puede estar vacío...");
        }
        if (ChronoUnit.DAYS.between(fechaInicio, fechaFin) < 0) {
            throw new Valores_Invalidos("La fecha de fin no puede ser anterior a la fecha de inicio.");
        }
        if (costoTotal != null && costoTotal < 0) {
            throw new Valores_Invalidos("El costo total no puede ser un valor negativo...");
        }

        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costoTotal = costoTotal;
        this.estaPagada = estaPagada;
        this.vehiculoPatente = vehiculoPatente;
        this.clienteDni = clienteDni;
    }

    public Reservas(int idReserva, LocalDate fechaInicio, LocalDate fechaFin, Float costoTotal, boolean estaPagada, String vehiculoPatente, String clienteDni) {
        this.idReserva = idReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.costoTotal = costoTotal;
        this.estaPagada = estaPagada;
        this.vehiculoPatente = vehiculoPatente;
        this.clienteDni = clienteDni;
    }

    public int Calcular_Dias() {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        if (dias < 1) {
            dias = 1;
        }
        return (int) dias;
    }

    @Override
    public Integer Obtener_Id() {
        return idReserva;
    }

    @Override
    public String toString() {
        return "Reservas{" +
                "idReserva=" + idReserva +
                ", fechaInicio=" + fechaInicio +
                ", fechaFin=" + fechaFin +
                ", costoTotal=" + costoTotal +
                ", estaPagada=" + estaPagada +
                ", vehiculoPatente='" + vehiculoPatente + '\'' +
                ", clienteDni='" + clienteDni + '\'' +
                '}';
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public Float getCostoTotal() {
        return costoTotal;
    }

    public void setCostoTotal(Float costoTotal) {
        this.costoTotal = costoTotal;
    }

    public boolean isEstaPagada() {
        return estaPagada;
    }

    public void setEstaPagada(boolean estaPagada) {
        this.estaPagada = estaPagada;
    }

    public String getVehiculoPatente() {
        return vehiculoPatente;
    }

    public String getClienteDni() {
        return clienteDni;
    }
}
