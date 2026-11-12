public class Auto extends Vehiculo {
    private int cantidad_Puertas;
    private boolean baul_grande;

    public Auto(String patente, String marca, String modelo, double tarifaDiaria, Estado_Vehiculo estadoVehiculo, int cantidad_Puertas, boolean baul_grande) throws Valores_Invalidos {
        super(patente, marca, modelo, tarifaDiaria, estadoVehiculo);
        if(cantidad_Puertas < 3 || cantidad_Puertas > 5){throw new Valores_Invalidos("Numero de puertas invalido (tiene que ser 3, 4 o 5)...");}
        this.cantidad_Puertas = cantidad_Puertas;
        this.baul_grande = baul_grande;
    }

    @Override
    public String toString() {
        return "Auto{" + super.toString() +
                ", cantidad_Puertas=" + cantidad_Puertas +
                ", baul_grande=" + baul_grande +
                '}';
    }

    @Override
    public double Calcular_Costos(int dias) {
        return getTarifaDiaria() * dias;
    }

    public int getCantidad_Puertas() {
        return cantidad_Puertas;
    }

    public boolean isBaul_grande() {
        return baul_grande;
    }
}
