public class Camioneta extends Vehiculo {
    private double capacidadCargakg;
    private boolean traccion4x4;

    public Camioneta(String patente, String marca, String modelo, double tarifaDiaria, Estado_Vehiculo estadoVehiculo, double capacidadCargakg, boolean traccion4x4) throws Valores_Invalidos {
        super(patente, marca, modelo, tarifaDiaria, estadoVehiculo);
        if(capacidadCargakg <= 0){throw new Valores_Invalidos("La Capacidad de Carga tiene que ser mayor a 0...");}
        this.capacidadCargakg = capacidadCargakg;
        this.traccion4x4 = traccion4x4;
    }

    @Override
    public String toString() {
        return "Camioneta{" + super.toString() +
                ", capacidadCargakg=" + capacidadCargakg +
                ", traccion4x4=" + traccion4x4 +
                '}';
    }

    @Override
    public double Calcular_Costos(int dias) {
        double costo = getTarifaDiaria() * dias;
        if(traccion4x4){
            costo = costo * 1.10;
        }
        return costo;
    }

    public double getCapacidadCargakg() {
        return capacidadCargakg;
    }

    public void setCapacidadCargakg(double capacidadCargakg) {
        this.capacidadCargakg = capacidadCargakg;
    }

    public boolean isTraccion4x4() {
        return traccion4x4;
    }

    public void setTraccion4x4(boolean traccion4x4) {
        this.traccion4x4 = traccion4x4;
    }
}
